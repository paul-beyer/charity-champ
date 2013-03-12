package com.charitychamp

import java.math.RoundingMode

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
		def returnObject = commonDepartmentActivityReturnValues(departmentInstance, currentCampaign)
		def groupTotals = donationService.groupTotals(departmentInstance, currentCampaign)
		returnObject.put('groupTotals', groupTotals)
		
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
		def totalMoneyEarned = new BigDecimal('0')
		def totalMealsEarned = new BigDecimal('0')
		groupTotals.each{
			if(it.amount){
				totalMoneyEarned = totalMoneyEarned.add(it.amount)
			}
			if(it.mealCount){
				totalMealsEarned = totalMealsEarned.add(it.mealCount)
			}
		}
		returnObject.put('totalMoneyEarned', totalMoneyEarned)
		returnObject.put('totalMealsEarned', totalMealsEarned)
		
		BigDecimal percentageMoneyGoal = BigDecimal.ZERO
		if(departmentMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMoneyGoal = totalMoneyEarned.divide(departmentMoneyGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMoneyGoal = rounded(decimalMoneyGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMoneyGoal', percentageMoneyGoal)
		
		BigDecimal percentageMealGoal = BigDecimal.ZERO
		if(departmentMealGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMealGoal = totalMealsEarned.divide(departmentMealGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMealGoal = rounded(decimalMealGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMealGoal', percentageMealGoal)
	
		// 5. Show team number
		// 6. Show variance and change color based on positive or negative
	
		
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
