package com.charitychamp

import static org.junit.Assert.*
import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

class CharityChampUtilsSpec extends UnitSpec {

	def "Calling currentCampaign should return the campaingn we are currently in"() {
		
		//Campaigns are set up in Bootstrap.groovy
		setup:
				
		def currentDate = new LocalDate(2012, 10, 25)
		
		
		when:
		
		def foundCampaign = CharityChampUtils.currentCampaign(currentDate)
		
		then:
	
		foundCampaign.name == 'Second Campaign'

	}
	
	def "Find First Campaign when date is the first day of campaign"() {
		
		//Campaigns are set up in Bootstrap.groovy
		setup:
				
		def currentDate = new LocalDate(2011, 1, 1)
		
		
		when:
		
		def foundCampaign = CharityChampUtils.currentCampaign(currentDate)
		
		then:
	
		foundCampaign.name == 'First Campaign'

	}
	
	def "Find First Campaign when date is the last day of campaign"() {
		
		//Campaigns are set up in Bootstrap.groovy
		setup:
				
		def currentDate = new LocalDate(2011, 12, 31)
		
		
		when:
		
		def foundCampaign = CharityChampUtils.currentCampaign(currentDate)
		
		then:
	
		foundCampaign.name == 'First Campaign'

	}
	
	def "Find Second Campaign when date is the first day of campaign"() {
		
		//Campaigns are set up in Bootstrap.groovy
		setup:
				
		def currentDate = new LocalDate(2012, 1, 1)
		
		
		when:
		
		def foundCampaign = CharityChampUtils.currentCampaign(currentDate)
		
		then:
	
		foundCampaign.name == 'Second Campaign'

	}
	
		
	def "Calling currentCampaign should return null if more than one campaign is found"() {
		
		//Campaigns are set up in Bootstrap.groovy
		setup:
		
		DateTime startDateThree = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDateThree = new DateTime(2013, 12 , 30, 0, 0)
		
		def campaign = new Campaign(name : "Fourth Campaign", startDate : startDateThree.toDate(), endDate : endDateThree.toDate()).save(flush:true)
		def currentDate = new LocalDate(2013, 10, 25)
		
		
		when:
		
		def foundCampaign = CharityChampUtils.currentCampaign(currentDate)
		
		then:
	
		foundCampaign == null

	}
	
	def "Calling currentCampaign should return null if no campaign is found"() {
		
		//Campaigns are set up in Bootstrap.groovy
		setup:
	
		def currentDate = new LocalDate(2014, 10, 25)
		
		
		when:
		
		def foundCampaign = CharityChampUtils.currentCampaign(currentDate)
		
		then:
	
		foundCampaign == null

	}
	
	def "donation date occurs within the campaign"(){
		
		setup:
		def campaign = Campaign.findByName("Second Campaign")
		def date = new LocalDate(2012, 4, 10)
		
		when:
		
		def isDateGood = CharityChampUtils.donationOccursWithinValidCampaign(campaign, date.toDate())
		
		then:
		
		isDateGood == true
		
		
		
		
	}
	
	def "donation date occurs on first day of the campaign"(){
		
		setup:
		def campaign = Campaign.findByName("Second Campaign")
		def date = new LocalDate(2012, 1, 1)
		
		when:
		
		def isDateGood = CharityChampUtils.donationOccursWithinValidCampaign(campaign, date.toDate())
		
		then:
		
		isDateGood == true
		
		
		
		
	}
	
	def "donation date occurs on last day of the campaign"(){
		
		setup:
		def campaign = Campaign.findByName("Second Campaign")
		def date = new LocalDate(2012, 12, 31)
		
		when:
		
		def isDateGood = CharityChampUtils.donationOccursWithinValidCampaign(campaign, date.toDate())
		
		then:
		
		isDateGood == true
		
		
		
		
	}
	
	def "donation date occurs on one day before the start of the campaign"(){
		
		setup:
		def campaign = Campaign.findByName("Second Campaign")
		def date = new LocalDate(2011, 12, 31)
		
		when:
		
		def isDateGood = CharityChampUtils.donationOccursWithinValidCampaign(campaign, date.toDate())
		
		then:
		
		isDateGood == false
		
		
		
		
	}
	
	def "donation date occurs on one day after the end of the campaign"(){
		
		setup:
		def campaign = Campaign.findByName("Second Campaign")
		def date = new LocalDate(2013, 1, 1)
		
		when:
		
		def isDateGood = CharityChampUtils.donationOccursWithinValidCampaign(campaign, date.toDate())
		
		then:
		
		isDateGood == false
		
		
		
		
	}
	
//	def "find the most recent number of meals a dollar buys"(){
//		
//		setup:
//		def 
//		
//	}
}
