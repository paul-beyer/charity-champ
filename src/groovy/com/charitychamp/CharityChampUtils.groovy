package com.charitychamp

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

}
