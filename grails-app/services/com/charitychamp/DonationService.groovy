package com.charitychamp

import java.math.BigDecimal;

class DonationService {

    def List donationList(Campaign campaign, OrganizationalUnit orgUnit, String type) {
		
		log.trace("Entering donationsList")
		def donationSources = campaign.donationSources
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
		
		def donationSources = campaign.donationSources
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
	
	private BigDecimal totalMealsActivityBuys(BigDecimal amount, Date donationDate){
				
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(donationDate)
		return rounded(amount.multiply(numberOfMealsDollarBuys))
			
	}
	
	private BigDecimal rounded(BigDecimal aNumber){
		return aNumber.setScale(CharityChampConstants.DECIMALS, CharityChampConstants.ROUNDING_MODE);
	 }
	

	
}
