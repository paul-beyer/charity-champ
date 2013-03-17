package com.charitychamp

import java.math.BigDecimal;

import org.joda.time.DateTime
import org.joda.time.LocalDate


class CharityChampUtils {
	
	//I should have used Grails criteria builder for this but I couldn't get it to work.
	//I think I was having date interoperability problems.  Converting the DateTime argument
	//to a Date did not work.  I think if I could have figured out how to convert the Date
	//in Campaign to DateTime within the Criteria, it probably would have worked.
	
	public static final Campaign currentCampaign(LocalDate dateTime){
	

		def defaultList = Campaign.findAll()
		def foundList = new ArrayList()
		defaultList.each{
			def jodaStartDate = new LocalDate(it.startDate)
			def jodaStartMinusOneDay = jodaStartDate.minusDays(1)
			def jodaEndDate = new LocalDate(it.endDate)
			def jodaEndPlusOneday = jodaEndDate.plusDays(1)
			if (jodaStartMinusOneDay.isBefore(dateTime) && jodaEndPlusOneday.isAfter(dateTime)){
				foundList << it
			}
		}
		
		if(foundList.size() > 1){
			return null
		}
		return foundList[0]
			
	}
	
	public static final String campaignTitle(LocalDate dateTime){
		
		def currentCampaign = currentCampaign(dateTime)
		
		def campaignString = "No campaign found for today's date (Setup Campaign in Configuration)"
		
		if(currentCampaign){
			campaignString = currentCampaign.toString()
		}
		
		return campaignString
		
	}
	
	public static final boolean donationOccursWithinValidCampaign(Campaign campaign, Date donationDate){
		
		def depositDate = new LocalDate(donationDate)
		def startDate = new LocalDate(campaign.startDate)
		def endDate = new LocalDate(campaign.endDate)
		def startDateMinusOne = startDate.minusDays(1)
		def endDatePlusOne = endDate.plusDays(1)
		def boolean isGood = false
		
		if(depositDate.isAfter(startDateMinusOne) && depositDate.isBefore(endDatePlusOne)){
			isGood = true
			
		}
		
		return isGood

	}
	
	public static final BigDecimal findNumberOfMealsADollarBuys(Date donationDate){
		
		def numberOfMealsADollarBuys = new BigDecimal('0')
		if(donationDate){
			
			def mealsADollarBuys = GlobalNumericSetting.createCriteria().list {
				and {
					eq("name", CharityChampConstants.MEALS_A_DOLLAR_BUYS_NAME_VALUE)
					le("effectiveDate", donationDate)
							
				}
				order("effectiveDate", "desc")
			}
			
			if(mealsADollarBuys){
				numberOfMealsADollarBuys = mealsADollarBuys.get(0).value
			}
			
		}
		
		return numberOfMealsADollarBuys
	}
	
	public static final BigDecimal findCurrentGoalForEmployee(Date currentDate){
		
		def goalAmountPerEmployee = new BigDecimal('0')
		
		if(currentDate){
			
			def goalsPerEmployee = GlobalNumericSetting.createCriteria().list {
				and {
					eq("name", CharityChampConstants.GOAL_PER_EMPLOYEE_NAME_VALUE)
					le("effectiveDate", currentDate)
							
				}
				order("effectiveDate", "desc")
			}
			
			if(goalsPerEmployee){
				goalAmountPerEmployee = goalsPerEmployee.get(0).value
			}
			
		} 
		
		return goalAmountPerEmployee
		
	}
	
	public static final BigDecimal rounded(BigDecimal aNumber){
		return aNumber.setScale(CharityChampConstants.DECIMALS, CharityChampConstants.ROUNDING_MODE);
	}
	
	

}
