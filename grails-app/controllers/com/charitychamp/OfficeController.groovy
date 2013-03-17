package com.charitychamp

import java.util.Map;

import org.joda.time.LocalDate
import org.springframework.dao.DataIntegrityViolationException

class OfficeController {

	def donationService 
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def overview(Long id) {
		
		log.debug("Entering office overview")

		def officeInstance = Office.get(id)
		if (!officeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
			
		// 1. get a total of meals and money for each department
		def returnObject = commonOfficeActivityReturnValues(officeInstance, currentCampaign)
		def departmentTotals = donationService.departmentTotals(officeInstance, currentCampaign)
		returnObject.put('departmentTotals', departmentTotals)
		
		// 2. get the total number of employees
		def departments = officeInstance.departments
		def employeeCount = 0
		if(departments){
			departments.each{
				employeeCount += it.numberOfEmployees.intValue()
			}
		}
		returnObject.put('employeeCount', employeeCount)	
		
		// 3. Calculate the goal for money
		def goalPerEmployee = CharityChampUtils.findCurrentGoalForEmployee(new LocalDate().toDate())
		def officeMoneyGoal = new BigDecimal('0')
		if(employeeCount > 0){
			def bigDecimalEmployeeCount = new BigDecimal(employeeCount)
			officeMoneyGoal = CharityChampUtils.rounded(goalPerEmployee.multiply(bigDecimalEmployeeCount))
		}
		returnObject.put("goalPerEmployee", goalPerEmployee)
		returnObject.put('moneyGoal', officeMoneyGoal)
		
		// 4. Calculate the goal for meals
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(new LocalDate().toDate())
	
		def officeMealGoal = new BigDecimal('0')
		if(officeMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			officeMealGoal = CharityChampUtils.rounded(officeMoneyGoal.multiply(numberOfMealsDollarBuys))
		}
		returnObject.put('mealGoal', CharityChampConstants.formatter.format(officeMealGoal))
				
		// 5. Calculate percentage of goals for both
		def totalMoneyEarned = new BigDecimal('0')
		def totalMealsEarned = new BigDecimal('0')
		departmentTotals.each{
			if(it.amount){
				totalMoneyEarned = totalMoneyEarned.add(it.amount)
			}
			if(it.mealCount){
				totalMealsEarned = totalMealsEarned.add(it.mealCount)
			}
		}
		returnObject.put('totalMoneyEarned', totalMoneyEarned)
		returnObject.put('totalMealsEarned', CharityChampConstants.formatter.format(totalMealsEarned))
		
		// 6. Calculate percentage goals
		BigDecimal percentageMoneyGoal = BigDecimal.ZERO
		if(officeMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMoneyGoal = totalMoneyEarned.divide(officeMoneyGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMoneyGoal = CharityChampUtils.rounded(decimalMoneyGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMoneyGoal', CharityChampConstants.formatter.format(percentageMoneyGoal))
		
		BigDecimal percentageMealGoal = BigDecimal.ZERO
		if(officeMealGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMealGoal = totalMealsEarned.divide(officeMealGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMealGoal = CharityChampUtils.rounded(decimalMealGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMealGoal', CharityChampConstants.formatter.format(percentageMealGoal))
			
		// 6. Show variance and change color based on positive or negative
				
		BigDecimal moneyVariant = totalMoneyEarned.subtract(officeMoneyGoal)
		BigDecimal mealVariant = totalMealsEarned.subtract(officeMealGoal)
	
		returnObject.put('moneyVariant', moneyVariant)
		returnObject.put('mealVariant', mealVariant)
		
		return returnObject
		
		
	}
	
	private Map commonOfficeActivityReturnValues(Office officeInstance, Campaign currentCampaign){
		
		[officeInstance: officeInstance, officeName : officeInstance?.name, officeId : officeInstance?.id
			, businessId : officeInstance?.business?.id, businessName : officeInstance?.business?.name
			, companyId :  officeInstance?.business?.company?.id,  companyName :  officeInstance?.business?.company?.name
			, currentCampaign : currentCampaign]
		
	}
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [officeInstanceList: Office.list(params), officeInstanceTotal: Office.count()]
    }

    def create() {
        [officeInstance: new Office(params)]
    }

    def save() {
        def officeInstance = new Office(params)
        if (!officeInstance.save(flush: true)) {
            render(view: "create", model: [officeInstance: officeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'office.label', default: 'Office'), officeInstance.id])
        redirect(action: "show", id: officeInstance.id)
    }

    def show(Long id) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        [officeInstance: officeInstance]
    }

    def edit(Long id) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        [officeInstance: officeInstance]
    }

    def update(Long id, Long version) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (officeInstance.version > version) {
                officeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'office.label', default: 'Office')] as Object[],
                          "Another user has updated this Office while you were editing")
                render(view: "edit", model: [officeInstance: officeInstance])
                return
            }
        }

        officeInstance.properties = params

        if (!officeInstance.save(flush: true)) {
            render(view: "edit", model: [officeInstance: officeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'office.label', default: 'Office'), officeInstance.id])
        redirect(action: "show", id: officeInstance.id)
    }

    def delete(Long id) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        try {
            officeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "show", id: id)
        }
    }
}
