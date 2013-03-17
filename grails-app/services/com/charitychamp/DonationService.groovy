package com.charitychamp

import java.math.BigDecimal;

class DonationService {

    def List donationList(Campaign campaign, OrganizationalUnit orgUnit, String type) {
		
		log.trace("Entering donationsList")
		def donationSources = campaign?.donationSources
		def foundDonations = new ArrayList()
		if(donationSources){
			donationSources.each {
				if(it.donation?.type == type && it.orgUnit?.name == orgUnit.name){
					foundDonations << it.donation
				}	
				
			}

		}
		def sortedDonations = foundDonations.sort{it.id}
		return sortedDonations
		

    }
	
	def DonationSource findDonationSource(Campaign campaign, Long donationId, String type){
		
		log.trace("Entering findDonatinWithinCampaign")
	
		def foundDonation = null
		
		def donationSources = campaign?.donationSources
		if(donationSources){
			donationSources.each {
			
					if(it.donation?.id == donationId  && it.donation?.type == type){
						foundDonation = it
						return foundDonation
					}
				
			}
		}
		
		return foundDonation
	}
	
	def SummaryItem jeansSummary(List jeansPayments){
		
		log.trace("Entering jeansSummary")
		def jeansSummary = null
		
		if(jeansPayments){
			def totalJeansAmount = new BigDecimal('0')
			def totalMealCount = new BigDecimal('0')
			jeansSummary = new SummaryItem(name : "Jeans")
			jeansPayments.each {
				totalJeansAmount = totalJeansAmount.add(it.amountCollected)
				def mealCount = totalMealsActivityBuys(it.amountCollected, it.donationDate)
				totalMealCount = totalMealCount.add(mealCount)
			}
			
			jeansSummary.amount = totalJeansAmount
			jeansSummary.mealCount = totalMealCount
			
		}
		
		return jeansSummary
	}
	
	def SummaryItem shiftSummary(List shiftList){
		
		log.trace("Entering shiftSummary")
		def shiftSummary = null
		
		if(shiftList){
			def totalNumberOfMeals = new BigDecimal('0') 
			shiftSummary = new SummaryItem(name : 'Food Bank Shifts')
			shiftList.each{
				totalNumberOfMeals = totalNumberOfMeals.add(it.numberOfMeals)
				
			}
			shiftSummary.mealCount = totalNumberOfMeals
			
		}
		return shiftSummary
		
	}
	
	def List activitySummary(List activities){
	
		
		log.trace("Entering activitySummary")
		def summaryList = []
		
		def activityTypes = StringList.findAllByListName(CharityChampConstants.ACTIVITY_TYPE, [sort : "value"])
		
		activityTypes.each{
			def activitySummary = extractActivitySummary(it.value, activities)
			if(activitySummary){
				
				summaryList.add(activitySummary)
				
			}	
		}
		
		return summaryList
	}
	
	private SummaryItem extractActivitySummary(String name, List activities){
		
		
		def activitySummary = null
		def foundActivities = []
	
		activities.each {
		
			if(it.name == name){
				foundActivities.add(it)
			}
		}
		
		if(foundActivities){
		
			activitySummary = new SummaryItem(name : name)
			BigDecimal totalForActivity = new BigDecimal('0')
			BigDecimal totalMealCount = new BigDecimal('0')
			
			foundActivities.each{
				def mealCount = totalMealsActivityBuys(it.amountCollected, it.donationDate)
				totalMealCount = totalMealCount.add(mealCount)
				totalForActivity = totalForActivity.add(it.amountCollected)
				
			}
		
			activitySummary.amount = totalForActivity
			activitySummary.mealCount = totalMealCount

				
		}
		
		return activitySummary
	}
	
	def List groupTotals(OrganizationalUnit department, Campaign currentCampaign){
		
		def summaryList = []
		def groups = department.groups
		if(groups){
			groups.each{
				def summary = groupSummary(it, currentCampaign)
				summaryList << summary
			}
			
		}
		def sortedSummaryList = summaryList.sort{it.name}
		
		return sortedSummaryList
	}
	
	private SummaryItem groupSummary(OrganizationalUnit group, Campaign currentCampaign){
		
		def summaryItem = new SummaryItem(orgUnitId : group.id, name: group.name)
			
		def activityList = donationList(currentCampaign, group, CharityChampConstants.ACTIVITY)
		def jeansList = donationList(currentCampaign, group, CharityChampConstants.JEANS_PAYMENT)
		def shiftList = donationList(currentCampaign, group, CharityChampConstants.MID_OHIO_FOOD_BANK_SHIFT)
		
		BigDecimal summaryTotal = new BigDecimal('0')
		BigDecimal mealsTotal = new BigDecimal('0')
		
		BigDecimal activityTotal = new BigDecimal('0')
		BigDecimal activityMealsTotal = new BigDecimal('0')
		
		BigDecimal jeansTotal = new BigDecimal('0')
		BigDecimal jeansMealsTotal = new BigDecimal('0')
		
		BigDecimal foodShiftTotal = new BigDecimal('0')
		
		activityList.each {
			activityTotal = activityTotal.add(it.amountCollected)
			activityMealsTotal = activityMealsTotal.add(totalMealsActivityBuys(it.amountCollected, it.donationDate))
		}
		jeansList.each{
			jeansTotal = jeansTotal.add(it.amountCollected)
			jeansMealsTotal = jeansMealsTotal.add(totalMealsActivityBuys(it.amountCollected, it.donationDate))
		}
		shiftList.each{
			foodShiftTotal = foodShiftTotal.add(it.numberOfMeals)
		}
		
		summaryTotal = summaryTotal.add(activityTotal)
		mealsTotal = mealsTotal.add(activityMealsTotal)
		
		summaryTotal = summaryTotal.add(jeansTotal)
		mealsTotal = mealsTotal.add(jeansMealsTotal)
		
		mealsTotal = mealsTotal.add(foodShiftTotal)
		
		summaryItem.amount = summaryTotal
		summaryItem.mealCount = mealsTotal
		
		return summaryItem
		
		
	}
	
	def List departmentTotals(OrganizationalUnit officeInstance, Campaign currentCampaign){
		
		def summaryList = []
		def departments = officeInstance.departments
		if(departments){
			departments.each{
				def summary = departmentSummary(it, currentCampaign)
				summaryList << summary
					
			}
				
		}
					

		def sortedSummaryList = summaryList.sort{it.name}
		
		return sortedSummaryList
		
		
	}
	
	private SummaryItem departmentSummary(OrganizationalUnit department, Campaign currentCampaign){
		
		def summaryItem = new SummaryItem(orgUnitId : department.id, name: department.name)
		
		def groupSummaryList = []
		def groups = department.groups
		if(groups){
			groups.each{
				def summary = groupSummary(it, currentCampaign)
				groupSummaryList << summary
			}
		}
		def deptMoneyTotal = new BigDecimal('0')
		def deptMealTotal = new BigDecimal('0')
		
		groupSummaryList.each{
			deptMoneyTotal = deptMoneyTotal.add(it.amount)
			deptMealTotal = deptMealTotal.add(it.mealCount)
		}
		
		summaryItem.amount = deptMoneyTotal
		summaryItem.mealCount = deptMealTotal
				
		return summaryItem
		
		
	}
	
	def List officeTotals(OrganizationalUnit businessInstance, Campaign currentCampaign){
		
		def summaryList = []
		def offices = businessInstance.offices
		if(offices){
			offices.each{
				def summary = officeSummary(it, currentCampaign)
				summaryList << summary
					
			}
				
		}
					

		def sortedSummaryList = summaryList.sort{it.name}
		
		return sortedSummaryList
		
		
	}
	
	private SummaryItem officeSummary(OrganizationalUnit office, Campaign currentCampaign){
		
		def summaryItem = new SummaryItem(orgUnitId : office.id, name: office.name)
		
		def departmentSummaryList = []
		def departments = office.departments
		if(departments){
			departments.each{
				def summary = departmentSummary(it, currentCampaign)
				departmentSummaryList << summary
			}
		}
		def officeMoneyTotal = new BigDecimal('0')
		def officeMealTotal = new BigDecimal('0')
		
		departmentSummaryList.each{
			officeMoneyTotal = officeMoneyTotal.add(it.amount)
			officeMealTotal = officeMealTotal.add(it.mealCount)
		}
		
		summaryItem.amount = officeMoneyTotal
		summaryItem.mealCount = officeMealTotal
				
		return summaryItem
		
		
	}
	
	def List businessTotals(OrganizationalUnit companyInstance, Campaign currentCampaign){
		
		def summaryList = []
		def businesses = companyInstance.businesses
		if(businesses){
			businesses.each{
				def summary = businessSummary(it, currentCampaign)
				summaryList << summary
					
			}
				
		}
					

		def sortedSummaryList = summaryList.sort{it.name}
		
		return sortedSummaryList
		
		
	}
	
	private SummaryItem businessSummary(OrganizationalUnit business, Campaign currentCampaign){
		
		def summaryItem = new SummaryItem(orgUnitId : business.id, name: business.name)
		
		def officeSummaryList = []
		def offices = business.offices
		if(offices){
			offices.each{
				def summary = officeSummary(it, currentCampaign)
				officeSummaryList << summary
			}
		}
		def businessMoneyTotal = new BigDecimal('0')
		def businessMealTotal = new BigDecimal('0')
		
		officeSummaryList.each{
			businessMoneyTotal = businessMoneyTotal.add(it.amount)
			businessMealTotal = businessMealTotal.add(it.mealCount)
		}
		
		summaryItem.amount = businessMoneyTotal
		summaryItem.mealCount = businessMealTotal
				
		return summaryItem
		
		
	}
	
	private BigDecimal totalMealsActivityBuys(BigDecimal amount, Date donationDate){
				
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(donationDate)
		return CharityChampUtils.rounded(amount.multiply(numberOfMealsDollarBuys))
			
	}
	
	
	
}
