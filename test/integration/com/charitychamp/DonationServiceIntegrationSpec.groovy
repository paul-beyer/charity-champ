package com.charitychamp;

import static org.junit.Assert.*
import grails.plugin.spock.UnitSpec

import org.joda.time.DateTime
import org.joda.time.LocalDate

class DonationServiceIntegrationSpec extends UnitSpec{
	
	def "calling activitySummary should return a list with name of ActivitySummary"(){
		
		setup:
		
		def donationService = new DonationService()
			
		LocalDate mealsDate = new LocalDate(2011,1,1)
		LocalDate donationDate = new LocalDate(2012,1,1)
			
		Donation activity1 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('100.00'), donationDate: donationDate.toDate())
		Donation activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('150.00'), donationDate: donationDate.toDate())
		Donation activity3 = new Activity(name: 'Chili Cookoff', amountCollected : new BigDecimal('38.75'), donationDate: donationDate.toDate())
		Donation activity4 = new Activity(name: 'Snack Sales', amountCollected : new BigDecimal('42.67'), donationDate: donationDate.toDate())
		Donation activity5 = new Activity(name: 'Snack Sales', amountCollected : new BigDecimal('98.42'), donationDate: donationDate.toDate())
	
		def activityList = [activity1,activity2,activity3,activity4,activity5]
						 
		when:
		def foundSummaries = donationService.activitySummary(activityList)
	 
			   
		then:
		foundSummaries.size() == 3
		foundSummaries*.name == ['BakeOff', 'Chili Cookoff', 'Snack Sales']
		foundSummaries*.amount == [250, 38.75, 141.09]
		//amounts are based on a multiplier of 40 from an integration test in CharityChampUtilsSpec
		foundSummaries*.mealCount == [10000, 1550, 5643.60]
	}
	
	def "return a list of SummaryItems when groupTotals is called"() {
		setup:
	
		def donationService = new DonationService()
 
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
	
		new GlobalNumericSetting(name : CharityChampConstants.MEALS_A_DOLLAR_BUYS_NAME_VALUE , mofbShift : false, value : new BigDecimal('3'), effectiveDate : startDate.toDate()).save(failOnError:true)
		def shiftType1 = new GlobalNumericSetting(name : 'ShiftType1' , mofbShift : true, value : new BigDecimal('33.3'), effectiveDate : startDate.toDate()).save(failOnError:true)
		def shiftType2 = new GlobalNumericSetting(name : 'ShiftType2' , mofbShift : true, value : new BigDecimal('133.3'), effectiveDate : startDate.toDate()).save(failOnError:true)
		def shiftType3 = new GlobalNumericSetting(name : 'ShiftType3' , mofbShift : true, value : new BigDecimal('103'), effectiveDate : startDate.toDate()).save(failOnError:true)
	 
		Person person = new Person(userId : "smithd", firstName : "Regan", lastName : "Smith", personTitle : "CEO", email : "smithd@gmail.com").save(failOnError:true)
		Person person2 = new Person(userId : "jonesj", firstName : "John", lastName : "Johns", personTitle : "dude", email : "jonesj@gmail.com").save(failOnError:true)
		OrganizationalUnit company = new Company(name: "Nationwide", leader : person).save()
		OrganizationalUnit business = new Business(name : "IT", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(failOnError:true)
		OrganizationalUnit office = new Office(name : "Enterprise Applications", leader : person, charityCaptain : person, business : business).save(failOnError:true)
		OrganizationalUnit department = new Department(name : "CIM/SOA", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(failOnError:true)
		Campaign campaign = new Campaign(name: "SummaryTest", startDate: startDate.toDate(), endDate: endDate.toDate()).save(failOnError:true)
		
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('100.00'), donationDate: donationDate.toDate()).save(failOnError:true)
		Donation mofbShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : shiftType1, donationDate: donationDate.toDate()).save(failOnError:true)
		Donation jeans = new JeansPayment(employeeUserId: 'person2', payerFirstName : 'Greg', payerLastName : 'Biffle', amountCollected : new BigDecimal('85.00'), donationDate: donationDate.toDate()).save(failOnError:true)
		
		Donation activity2 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('98.00'), donationDate: donationDate.toDate()).save(failOnError:true)
		Donation mofbShift2 = new VolunteerShift(numberOfVolunteers: 10, mealFactor : shiftType2, donationDate: donationDate.toDate()).save(failOnError:true)
		Donation jeans2 = new JeansPayment(employeeUserId: 'person2', payerFirstName : 'Greg', payerLastName : 'Biffle', amountCollected : new BigDecimal('22'), donationDate: donationDate.toDate()).save(failOnError:true)
		
		Donation activity3 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('77.65'), donationDate: donationDate.toDate()).save(failOnError:true)
		Donation mofbShift3 = new VolunteerShift(numberOfVolunteers: 10, mealFactor : shiftType3, donationDate: donationDate.toDate()).save(failOnError:true)
		Donation jeans3 = new JeansPayment(employeeUserId: 'person2', payerFirstName : 'Greg', payerLastName : 'Biffle', amountCollected : new BigDecimal('1200'), donationDate: donationDate.toDate()).save(failOnError:true)
		
		OrganizationalUnit group1 = new Group(name : 'Anglers',  leader : person, department : department).save(failOnError:true)
		OrganizationalUnit group2 = new Group(name : 'RadioHead',  leader : person, department : department).save(failOnError:true)
		OrganizationalUnit group3 = new Group(name : 'Shrikes',  leader : person, department : department).save(failOnError:true)
		
		def groups = []
		groups << group1
		groups << group2
		groups << group3
		
		department.groups = groups
		
		department.save(failOnError:true)
		
		
		DonationSource donationSource = new DonationSource()
		donationSource.donation = activity
		donationSource.orgUnit = group1
		donationSource.save(failOnError:true)
		
		DonationSource donationSource2 = new DonationSource()
		donationSource2.donation = mofbShift
		donationSource2.orgUnit = group1
		donationSource2.save(failOnError:true)
		
		DonationSource donationSource3 = new DonationSource()
		donationSource3.donation = jeans
		donationSource3.orgUnit = group1
		donationSource3.save(failOnError:true)
		
		DonationSource donationSource4 = new DonationSource()
		donationSource4.donation = activity2
		donationSource4.orgUnit = group2
		donationSource4.save(failOnError:true)
		
		DonationSource donationSource5 = new DonationSource()
		donationSource5.donation = mofbShift2
		donationSource5.orgUnit = group2
		donationSource5.save(failOnError:true)
		
		DonationSource donationSource6 = new DonationSource()
		donationSource6.donation = jeans2
		donationSource6.orgUnit = group2
		donationSource6.save(failOnError:true)
		
		DonationSource donationSource7 = new DonationSource()
		donationSource7.donation = activity3
		donationSource7.orgUnit = group3
		donationSource7.save(failOnError:true)
		
		DonationSource donationSource8 = new DonationSource()
		donationSource8.donation = mofbShift3
		donationSource8.orgUnit = group3
		donationSource8.save(failOnError:true)
		
		DonationSource donationSource9 = new DonationSource()
		donationSource9.donation = jeans3
		donationSource9.orgUnit = group3
		donationSource9.save(failOnError:true)
		
		List donations = new ArrayList()
		donations << donationSource
		donations << donationSource2
		donations << donationSource3
		donations << donationSource4
		donations << donationSource5
		donations << donationSource6
		donations << donationSource7
		donations << donationSource8
		donations << donationSource9
		
		campaign.donationSources = donations
		campaign.save(failOnError:true)
		
		when:
		def foundSummaries = donationService.groupTotals(department, campaign)
	 
			   
		then:
		foundSummaries.size() == 3
		foundSummaries*.name == ['Anglers', 'RadioHead', 'Shrikes']
		foundSummaries*.amount == [185.00, 120.00, 1277.65]
		foundSummaries*.mealCount == [888.00, 1693.00, 4862.95]
		
	 }

}
