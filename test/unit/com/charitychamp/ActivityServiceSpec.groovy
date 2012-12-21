package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(ActivityService)
@Mock([Campaign, Person, Company, Business, Office, Department, Group, Activity])
class ActivityServiceSpec extends Specification{
	
	def campaign
	def group
	def donationList
	
	def setup(){
		
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		campaign = new Campaign(name : "Campaign One", startDate : new Date(), endDate : new Date()).save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		group = new Group(name : 'SOA',  leader : person, department : department).save()
		
		Donation activity1 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: new Date()).save()
		Donation activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: new Date()).save()
		Donation activity3 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: new Date()).save()
		donationList = new ArrayList()
		donationList << activity1
		donationList << activity2
		donationList << activity3
		
	}

   
    def "calling activityList should return a list of activities"() {
       
		given:
		
		def activityService = new ActivityService()
		DonationService donationService = Mock()
		activityService.donationService = donationService
		
		
		1 * donationService.donationList(campaign, group, "activity") >> {donationList}
		
		when:
		def activities = activityService.activityList(group, campaign)
		
		then:
		activities.size() == 3
	
		
		
    }
	
	def "calling activityList with no campaign should result in empty list being returned"(){
		
		given:
		def activityService = new ActivityService()
		DonationService donationService = Mock()
		activityService.donationService = donationService
		
		
		0 * donationService.donationList(campaign, group, "activity") >> {donationList}
		
		when:
		def activities = activityService.activityList(group, null)
		
		then:
		activities.size() == 0
	}
}
