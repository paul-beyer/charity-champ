package com.charitychamp

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDate
import org.springframework.dao.DataIntegrityViolationException

class DepartmentController {
	
	def donationService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [departmentInstanceList: Department.list(params), departmentInstanceTotal: Department.count()]
    }

    def create() {
        [departmentInstance: new Department(params)]
    }
	
	def overview(Long id) {
		
		log.debug("Entering department overview")

		def departmentInstance = Department.get(id)
		if (!departmentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
			
		// 1. get a total of meals and money for each group, make groups clickable
		def returnObject = commonDepartmentActivityReturnValues(departmentInstance)
		returnObject.put('groupTotals', donationService.groupTotals(departmentInstance, currentCampaign))
		
		// 2. Calculate the goal for money
		def goalPerEmployee = CharityChampUtils.findCurrentGoalForEmployee(new LocalDate().toDate())
		def departmentMoneyGoal = new BigDecimal('0')
		if(departmentInstance.numberOfEmployees.value > 0){
			departmentMoneyGoal = rounded(goalPerEmployee.multiply(departmentInstance.numberOfEmployees))
		}
		returnObject.put('moneyGoal', departmentMoneyGoal)
		
		// 3. Calculate the goal for meals
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(new LocalDate().toDate())
	
		def departmentMealGoal = new BigDecimal('0')
		if(departmentMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			departmentMealGoal = rounded(departmentMoneyGoal.multiply(numberOfMealsDollarBuys))
		}
		returnObject.put('mealGoal', departmentMealGoal)
		
		
		// 4. Calculate percentage of goals for both
		// 5. Show team number
		// 6. Show variance and change color based on positive or negative
		
//		//get all activities
//		def activityList = []
//		def jeansList = []
//		def shiftList = []
//		if(currentCampaign){
//			activityList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.ACTIVITY)
//			jeansList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.JEANS_PAYMENT)
//			shiftList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.MID_OHIO_FOOD_BANK_SHIFT)
//		}
//		
//		//get total by each activity
//		def activitySummaries = donationService.activitySummary(activityList)
//		def jeansSummary = donationService.jeansSummary(jeansList)
//		activitySummaries.add(jeansSummary)
//			
//		
//		//total all the money
//		def totalMoney = new BigDecimal('0')
//		activitySummaries.each {
//			if(it?.amount){
//				totalMoney = totalMoney.add(it?.amount)
//			}
//		}
//		
//		def shiftSummary = donationService.shiftSummary(shiftList)
//		activitySummaries.add(shiftSummary)
//		
//		//total all the meals
//		def totalMeals = new BigDecimal('0')
//		activitySummaries.each {
//			if(it?.mealCount){
//				totalMeals = totalMeals.add(it?.mealCount)
//			}
//		}
//		
//						
//		returnObject.put('activitySummaries', activitySummaries)
//		returnObject.put('totalMoney', totalMoney)
//		returnObject.put('totalMeals', totalMeals)
		
		
		
		return returnObject
		
		
	}
	
	private Map commonDepartmentActivityReturnValues(Department departmentInstance, Campaign currentCampaign){
		
		[departmentInstance: departmentInstance, departmentName : departmentInstance?.name, departmentId : departmentInstance?.id
			, officeId : departmentInstance?.office?.id, officeName : departmentInstance?.office?.name
			, businessId : departmentInstance?.office?.business?.id, businessName : departmentInstance?.office?.business?.name
			, companyId :  departmentInstance?.office?.business?.company?.id,  companyName :  departmentInstance?.office?.business?.company?.name
			, currentCampaign : currentCampaign]
		
	}


    def save() {
        def departmentInstance = new Department(params)
        if (!departmentInstance.save(flush: true)) {
            render(view: "create", model: [departmentInstance: departmentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'department.label', default: 'Department'), departmentInstance.id])
        redirect(action: "show", id: departmentInstance.id)
    }

    def show(Long id) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        [departmentInstance: departmentInstance]
    }

    def edit(Long id) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        [departmentInstance: departmentInstance]
    }

    def update(Long id, Long version) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (departmentInstance.version > version) {
                departmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'department.label', default: 'Department')] as Object[],
                          "Another user has updated this Department while you were editing")
                render(view: "edit", model: [departmentInstance: departmentInstance])
                return
            }
        }

        departmentInstance.properties = params

        if (!departmentInstance.save(flush: true)) {
            render(view: "edit", model: [departmentInstance: departmentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'department.label', default: 'Department'), departmentInstance.id])
        redirect(action: "show", id: departmentInstance.id)
    }

    def delete(Long id) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        try {
            departmentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "show", id: id)
        }
    }
	
		
	private BigDecimal rounded(BigDecimal aNumber){
		return aNumber.setScale(CharityChampConstants.DECIMALS, CharityChampConstants.ROUNDING_MODE);
	}

}
