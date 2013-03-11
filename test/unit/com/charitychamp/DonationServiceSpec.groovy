
package com.charitychamp

import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.joda.time.DateTime
import org.joda.time.LocalDate


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */


@TestFor(DonationService)
class DonationServiceSpec extends UnitSpec{

	def setup(){

		mockDomain(Campaign)
		mockDomain(DonationSource)
		mockDomain(Donation)
		mockDomain(Group)
		mockDomain(Activity)
		mockDomain(Company)
		mockDomain(Business)
		mockDomain(Office)
		mockDomain(Department)
		mockDomain(Person)
		mockDomain(JeansPayment)
		mockDomain(VolunteerShift)
	}
 
    def "return a list of activitites when activityList is called"() {
       setup:
	   mockLogging(DonationService, true)
	   def donationService = new DonationService()

	   DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
	   DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
	   DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
	    
	
	   Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
	   OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
	   OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
	   OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
	   OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
	   Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
	   Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
	   OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
	   DonationSource donationSource = new DonationSource(donation : activity, orgUnit : group).save()
	   List donations = new ArrayList()
	   donations << donationSource
	   campaign.donationSources = donations
	   campaign.save()
	   

	   when:
	   def foundActivities = donationService.donationList(campaign, group, CharityChampConstants.ACTIVITY)
	
	   	   
	   then:
	   foundActivities.size() == 1
    }
	
	def "return a list of activitites when activityList is called when campaign has a mixture of donation types"() {
		setup:
		mockLogging(DonationService, true)
		def donationService = new DonationService()
 
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
		 
	 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
		Donation mofbShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : new BigDecimal(33.3), donationDate: donationDate.toDate()).save()
		Donation jeans = new JeansPayment(employeeUserId: 'biffleg', payerFirstName : 'Greg', payerLastName : 'Biffle', amountCollected : new BigDecimal(85.00), donationDate: donationDate.toDate()).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		DonationSource donationSource = new DonationSource(donation : activity, orgUnit : group).save()
		DonationSource donationSource2 = new DonationSource(donation : mofbShift, orgUnit : group).save()
		DonationSource donationSource3 = new DonationSource(donation : jeans, orgUnit : group).save()
		List donations = new ArrayList()
		donations << donationSource
		donations << donationSource2
		donations << donationSource3
		campaign.donationSources = donations
		campaign.save()
		
 
		when:
		def foundActivities = donationService.donationList(campaign, group, CharityChampConstants.ACTIVITY)
	 
			   
		then:
		foundActivities.size() == 1
	 }
	
	
	
	def "find correct donationSource with donation id and campaign"(){
		
		setup:
		mockLogging(DonationService, true)
		def donationService = new DonationService()
		
 
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
		 	 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
		Donation mofbShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : new BigDecimal(33.3), donationDate: donationDate.toDate()).save()
		Donation jeans = new JeansPayment(employeeUserId: 'biffleg', payerFirstName : 'Greg', payerLastName : 'Biffle', amountCollected : new BigDecimal(85.00), donationDate: donationDate.toDate()).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		DonationSource donationSource = new DonationSource(donation : activity, orgUnit : group).save()
		DonationSource donationSource2 = new DonationSource(donation : mofbShift, orgUnit : group).save()
		DonationSource donationSource3 = new DonationSource(donation : jeans, orgUnit : group).save()
		List donations = new ArrayList()
		donations << donationSource
		donations << donationSource2
		donations << donationSource3
		campaign.donationSources = donations
		campaign.save()
					 
		when:
		def foundDonationSource = donationService.findDonationSource(campaign, activity.id, CharityChampConstants.ACTIVITY)
	 
			   
		then:
		foundDonationSource?.donation?.type == CharityChampConstants.ACTIVITY
	}
	
	// this test is replaced by a test in DonationServiceIntegrationSpec called  "calling activitySummary should return a list with name of ActivitySummary"()
	// I'm leaving this here for reference only
	// I wrote as an integration test because I wanted to truly test findNumberOfMealsADollarBuys in CharityChampUtils
	
//	def "calling activitySummary should return a list with name of ActivitySummary"(){
//		
//		setup:
//		mockLogging(DonationService, true)
//		def donationService = new DonationService()
//		
//	
//		mockDomain(DonationSource)
//		
//		mockDomain(Group)
//		mockDomain(Activity)
//		mockDomain(Company)
//		mockDomain(Business)
//		mockDomain(Office)
//		mockDomain(Department)
//		mockDomain(Person)
//		mockDomain(JeansPayment)
//		mockDomain(VolunteerShift)
//		mockDomain(Donation)
//		mockDomain(Activity)
//		mockDomain(StringList)
//		mockDomain(GlobalNumericSetting)
//		
//		LocalDate mealsDate = new LocalDate(2011,1,1)
//		LocalDate donationDate = new LocalDate(2012,1,1)
//		
//		new GlobalNumericSetting(name : CharityChampConstants.MEALS_A_DOLLAR_BUYS_NAME_VALUE , mofbShift : false, value : new BigDecimal('3'), effectiveDate : mealsDate.toDate()).save()
//		def stringList3 = new StringList(listName : 'Activity Type', value : 'Snack Bar').save()
//		def stringList1 = new StringList(listName : 'Activity Type', value : 'BakeOff').save()
//		def stringList2 = new StringList(listName : 'Activity Type', value : 'Chili Cookoff').save()
//		
//		
//	 	def activity1 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('100.00'), donationDate: donationDate.toDate()).save()
//		def activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('150.00'), donationDate: donationDate.toDate()).save()
//		def activity3 = new Activity(name: 'Chili Cookoff', amountCollected : new BigDecimal('38.75'), donationDate: donationDate.toDate()).save()
//		def activity4 = new Activity(name: 'Snack Bar', amountCollected : new BigDecimal('42.67'), donationDate: donationDate.toDate()).save()
//		def activity5 = new Activity(name: 'Snack Bar', amountCollected : new BigDecimal('98.42'), donationDate: donationDate.toDate()).save()
//	
//		def activityList = [activity1,activity2,activity3,activity4,activity5]
//	
//		when:
//		def foundSummaries = donationService.activitySummary(activityList)
//	 
//			   
//		then:
//		foundSummaries.size() == 3
//		foundSummaries*.name == ['BakeOff', 'Chili Cookoff', 'Snack Bar']
//		foundSummaries*.amount == [250, 38.75, 141.09]
//		foundSummaries*.mealCount == [750, 116.25, 423.27]
//	}
	

}
