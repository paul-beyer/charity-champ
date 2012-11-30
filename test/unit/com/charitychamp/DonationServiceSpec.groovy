package com.charitychamp

import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.joda.time.DateTime
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */



class DonationServiceSpec extends UnitSpec{
 
    def "return a list of activitites when activityList is called"() {
       setup:
	   mockLogging(DonationService, true)
	   def donationService = new DonationService()
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
	   def foundActivities = donationService.donationList(campaign, group, CharityChampConstants.activity)
	
	   	   
	   then:
	   foundActivities.size() == 1
    }
	
	def "return a list of activitites when activityList is called when campaign has a mixture of donation types"() {
		setup:
		mockLogging(DonationService, true)
		def donationService = new DonationService()
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
		Donation jeans = new JeansPayment(employeeUserId: 'biffleg', payerFirstName : 'Greg', payerLastName : 'Biffle', amtPaid : new BigDecimal(85.00), donationDate: donationDate.toDate()).save()
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
		def foundActivities = donationService.donationList(campaign, group, CharityChampConstants.activity)
	 
			   
		then:
		foundActivities.size() == 1
	 }
	

}
