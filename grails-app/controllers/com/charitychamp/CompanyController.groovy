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

class CompanyController {

	def donationService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def overview(Long id) {
		
		log.debug("Entering comany overview")

		def companyInstance = Company.get(id)
		if (!companyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
			
		// 1. get a total of meals and money for each business
		def returnObject = commonCompanyActivityReturnValues(companyInstance, currentCampaign)
		def businessTotals = donationService.businessTotals(companyInstance, currentCampaign)
		returnObject.put('businessTotals', businessTotals)
		
		// 2. get the total number of employees
		def employeeCount = 0
		def businesses = companyInstance.businesses
		if(businesses){
			businesses.each{
				def offices = it.offices
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
			}
		}
		
		
		returnObject.put('employeeCount', employeeCount)
		
		// 3. Calculate the goal for money
		def goalPerEmployee = CharityChampUtils.findCurrentGoalForEmployee(new LocalDate().toDate())
		def companyMoneyGoal = new BigDecimal('0')
		if(employeeCount > 0){
			def bigDecimalEmployeeCount = new BigDecimal(employeeCount)
			companyMoneyGoal = CharityChampUtils.rounded(goalPerEmployee.multiply(bigDecimalEmployeeCount))
		}
		returnObject.put("goalPerEmployee", goalPerEmployee)
		returnObject.put('moneyGoal', companyMoneyGoal)
		
		// 4. Calculate the goal for meals
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(new LocalDate().toDate())
	
		def companyMealGoal = new BigDecimal('0')
		if(companyMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			companyMealGoal = CharityChampUtils.rounded(companyMoneyGoal.multiply(numberOfMealsDollarBuys))
		}
		returnObject.put('mealGoal', CharityChampConstants.formatter.format(companyMealGoal))
				
		// 5. Calculate percentage of goals for both
		def totalMoneyEarned = new BigDecimal('0')
		def totalMealsEarned = new BigDecimal('0')
		businessTotals.each{
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
		if(companyMoneyGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMoneyGoal = totalMoneyEarned.divide(companyMoneyGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMoneyGoal = CharityChampUtils.rounded(decimalMoneyGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMoneyGoal', CharityChampConstants.formatter.format(percentageMoneyGoal))
		
		BigDecimal percentageMealGoal = BigDecimal.ZERO
		if(companyMealGoal.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal decimalMealGoal = totalMealsEarned.divide(companyMealGoal,5,BigDecimal.ROUND_HALF_EVEN)
			percentageMealGoal = CharityChampUtils.rounded(decimalMealGoal.multiply(CharityChampConstants.PERCENTAGE))
		}
		
		returnObject.put('percentageMealGoal', CharityChampConstants.formatter.format(percentageMealGoal))
			
		// 6. Show variance and change color based on positive or negative
				
		BigDecimal moneyVariant = totalMoneyEarned.subtract(companyMoneyGoal)
		BigDecimal mealVariant = totalMealsEarned.subtract(companyMealGoal)
	
		returnObject.put('moneyVariant', moneyVariant)
		returnObject.put('mealVariant', mealVariant)
		
		return returnObject
		
		
	}
	
	private Map commonCompanyActivityReturnValues(Company companyInstance, Campaign currentCampaign){
		
		[companyInstance: companyInstance, companyName : companyInstance?.name, companyId : companyInstance?.id
			, currentCampaign : currentCampaign]
		
	}
	
	def load() {
		redirect(action: "overview", id: params.companyId.toLong())
		
	}
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [companyInstanceList: Company.list(params), companyInstanceTotal: Company.count()]
    }

    def create() {
        [companyInstance: new Company(params)]
    }

    def save() {
        def companyInstance = new Company(params)
        if (!companyInstance.save(flush: true)) {
            render(view: "create", model: [companyInstance: companyInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'company.label', default: 'Company'), companyInstance.id])
        redirect(action: "show", id: companyInstance.id)
    }

    def show(Long id) {
        def companyInstance = Company.get(id)
        if (!companyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }

        [companyInstance: companyInstance]
    }

    def edit(Long id) {
        def companyInstance = Company.get(id)
        if (!companyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }

        [companyInstance: companyInstance]
    }

    def update(Long id, Long version) {
        def companyInstance = Company.get(id)
        if (!companyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (companyInstance.version > version) {
                companyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'company.label', default: 'Company')] as Object[],
                          "Another user has updated this Company while you were editing")
                render(view: "edit", model: [companyInstance: companyInstance])
                return
            }
        }

        companyInstance.properties = params

        if (!companyInstance.save(flush: true)) {
            render(view: "edit", model: [companyInstance: companyInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'company.label', default: 'Company'), companyInstance.id])
        redirect(action: "show", id: companyInstance.id)
    }

    def delete(Long id) {
        def companyInstance = Company.get(id)
        if (!companyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }

        try {
            companyInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "show", id: id)
        }
    }
}
