/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.charitychamp

import org.joda.time.LocalDate
import org.springframework.dao.DataIntegrityViolationException

class BusinessController {

	def donationService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def overview(Long id) {
		
		log.debug("Entering business overview")

		def businessInstance = Business.get(id)
		if (!businessInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
			
		// 1. get a total of meals and money for each department
		def returnObject = commonBusinessActivityReturnValues(businessInstance, currentCampaign)
		def officeTotals = donationService.officeTotals(businessInstance, currentCampaign)
		returnObject.put('officeTotals', officeTotals)
		
		// 2. get the total number of employees
		def employeeCount = 0
		def offices = businessInstance.offices
		if(offices){
			offices.each{
				def departments = it.departments
				if(departments){
					departments.each{
						employeeCount += it.numberOfEmployees.intValue()
					}
				}
			}
		}
		
		returnObject.put('employeeCount', employeeCount)
		
		// 3. Calculate the goal for money
		def goalPerEmployee = CharityChampUtils.findCurrentGoalForEmployee(new LocalDate().toDate())
		def businessMoneyGoal = new BigDecimal('0')
		if(employeeCount > 0){
			def bigDecimalEmployeeCount = new BigDecimal(employeeCount)
			businessMoneyGoal = CharityChampUtils.rounded(goalPerEmployee.multiply(bigDecimalEmployeeCount))
		}
		returnObject.put("goalPerEmployee", goalPerEmployee)
		returnObject.put('moneyGoal', businessMoneyGoal)
		
		// 4. Calculate the goal for meals
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(new LocalDate().toDate())
	
		def businessMealGoal = new BigDecimal('0')
		if(businessMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			businessMealGoal = CharityChampUtils.rounded(businessMoneyGoal.multiply(numberOfMealsDollarBuys))
		}
		returnObject.put('mealGoal', CharityChampConstants.formatter.format(businessMealGoal))
				
		// 5. Calculate percentage of goals for both
		def totalMoneyEarned = new BigDecimal('0')
		def totalMealsEarned = new BigDecimal('0')
		officeTotals.each{
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
		if(businessMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMoneyGoal = totalMoneyEarned.divide(businessMoneyGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMoneyGoal = CharityChampUtils.rounded(decimalMoneyGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMoneyGoal', CharityChampConstants.formatter.format(percentageMoneyGoal))
		
		BigDecimal percentageMealGoal = BigDecimal.ZERO
		if(businessMealGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMealGoal = totalMealsEarned.divide(businessMealGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMealGoal = CharityChampUtils.rounded(decimalMealGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMealGoal', CharityChampConstants.formatter.format(percentageMealGoal))
			
		// 6. Show variance and change color based on positive or negative
				
		BigDecimal moneyVariant = totalMoneyEarned.subtract(businessMoneyGoal)
		BigDecimal mealVariant = totalMealsEarned.subtract(businessMealGoal)
	
		returnObject.put('moneyVariant', moneyVariant)
		returnObject.put('mealVariant', mealVariant)
		
		return returnObject
		
		
	}
	
	private Map commonBusinessActivityReturnValues(Business businessInstance, Campaign currentCampaign){
		
		[businessInstance: businessInstance, businessName : businessInstance?.name, businessId : businessInstance?.id
			, companyId :  businessInstance?.company?.id,  companyName :  businessInstance?.company?.name
			, currentCampaign : currentCampaign]
		
	}
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		
        params.max = Math.min(max ?: 10, 100)
		[businessInstanceList: Business.list(params), businessInstanceTotal: Business.count()]
    }

    def create() {
        [businessInstance: new Business(params)]
    }

    def save() {
	
        def businessInstance = new Business(params)
        if (!businessInstance.save(flush: true)) {
			
            render(view: "create", model: [businessInstance: businessInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'business.label', default: 'Business'), businessInstance.id])
        redirect(action: "show", id: businessInstance.id)
    }

    def show(Long id) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        [businessInstance: businessInstance]
    }

    def edit(Long id) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        [businessInstance: businessInstance]
    }

    def update(Long id, Long version) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (businessInstance.version > version) {
                businessInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'business.label', default: 'Business')] as Object[],
                          "Another user has updated this Business while you were editing")
                render(view: "edit", model: [businessInstance: businessInstance])
                return
            }
        }

        businessInstance.properties = params

        if (!businessInstance.save(flush: true)) {
            render(view: "edit", model: [businessInstance: businessInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'business.label', default: 'Business'), businessInstance.id])
        redirect(action: "show", id: businessInstance.id)
    }

    def delete(Long id) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        try {
            businessInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "show", id: id)
        }
    }
}
