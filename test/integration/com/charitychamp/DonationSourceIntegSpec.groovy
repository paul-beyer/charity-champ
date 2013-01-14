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


class DonationSourceIntegSpec extends UnitSpec {

	// no need to enforce a unique constraint on DonationSource.donation
//	def "saving a donationSource should fail if donation is the same one"(){
//		
//		setup:
//		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
//		Person person = new Person(userId : "howeyh", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
//		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
//		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
//		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
//		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
//		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
//		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
//		DonationSource donation = new DonationSource(donation: activity, orgUnit : group).save(flush : true)
//		activity.donationSource = donation
//		activity.save(flush : true)
//		
//		when:
//		DonationSource donation2 = new DonationSource(donation: activity, orgUnit : group)
//		
//		then:
//		donation2.save(flush:true) == null
//		
//	}
	
// no need for this test either....it's ok to have duplicate donations
//	def "saving a donationSource should fail if donation is a different object with the same values"(){
//		
//		setup:
//		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
//		Person person = new Person(userId : "howeyh", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
//		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
//		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
//		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
//		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
//		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
//		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
//		Donation activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
//		DonationSource donation = new DonationSource(donation: activity, orgUnit : group).save(flush : true)
//		
//		when:
//		DonationSource donation2 = new DonationSource(donation: activity2, orgUnit : group)
//		
//		then:
//		donation2.save(flush:true) == null
//		
//	}

	
	def "saving donationSource should pass if donation names are different"(){
		
		setup:
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
		Person person = new Person(userId : "cosbyb", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
		Donation activity2 = new Activity(name: 'Chili Cookoff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
		DonationSource donation = new DonationSource()
		donation.donation = activity
		donation.orgUnit = group
		donation.save(flush:true)
		
		when:
		DonationSource donation2 = new DonationSource()
		donation2.donation = activity2
		donation2.orgUnit = group
			
		then:
		donation2.save(flush:true) != null
		
		
	}
	
	def "saving donationSource should pass if donation amounts are different"(){
		
		setup:
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
		Person person = new Person(userId : "stewarts", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
		Donation activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(99.00), donationDate: donationDate.toDate()).save(flush : true)
		DonationSource donation = new DonationSource()
		donation.donation = activity
		donation.orgUnit = group
		donation.save(flush:true)
		
		when:
		DonationSource donation2 = new DonationSource()
		donation2.donation = activity2
		donation2.orgUnit = group
		
		then:
		donation2.save(flush:true) != null
		
		
	}
	
	def "saving donationSource should pass if donation dates are different"(){
		
		setup:
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
		DateTime donationDate2 = new DateTime(2013, 8 , 16, 0, 0)
		Person person = new Person(userId : "barqs", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
		Donation activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate2.toDate()).save(flush : true)
		DonationSource donation = new DonationSource()
		donation.donation = activity
		donation.orgUnit = group
		donation.save(flush:true)
		
		when:
		DonationSource donation2 = new DonationSource()
		donation2.donation = activity2
		donation2.orgUnit = group
		
		then:
		donation2.save(flush:true) != null
		
		
	}

}
