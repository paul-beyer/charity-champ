	package com.charitychamp

import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.joda.time.DateTime
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(CalculationService)
@Mock([Activity, JeansPayment,VolunteerShift,GlobalNumericSetting])
class CalculationServiceSpec extends UnitSpec{
	
	def donationDate
	def calcService

    def setup() {
	    donationDate = new DateTime(2013, 1 , 2, 0, 0)
		calcService = new CalculationService()
		
    }

    def "calling sumActivities should return 30 with 3 activities of 10 a piece"(){
		
		when:
	
		def activityOne = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('10.00'), donationDate: donationDate.toDate()).save()
		def activityTwo = new Activity(name: 'Snacks', amountCollected : new BigDecimal('10.00'), donationDate: donationDate.toDate()).save()
		def activityThree = new Activity(name: 'Cookoff', amountCollected : new BigDecimal('10.00'), donationDate: donationDate.toDate()).save()
		def activities  = [activityOne, activityTwo, activityThree]
		
		def total = calcService.sumDonations(activities)
		
		then:
		
		total.compareTo(new BigDecimal('30')) == 0
	}
	
	def "calling sumActivities with an empty list should return zero"(){
		
		when:
		
		def activities = []
		def total = calcService.sumDonations(activities)
		
		then:
		total.compareTo(new BigDecimal('0')) == 0
	}
   
	def "calling sumActivities with a null should return zero"(){
		
		when:
		
		def activities = null
		def total = calcService.sumDonations(activities)
		
		then:
		total.compareTo(new BigDecimal('0')) == 0
	}
	
	def "calling sumActivites with a wide range of positive numbers"(){
		
		when:
		def activities = []
		def activity1 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('10.43'), donationDate: donationDate.toDate()).save()
		activities << activity1
		def activity2 = new Activity(name: 'Snacks', amountCollected : new BigDecimal('189.56'), donationDate: donationDate.toDate()).save()
		activities << activity2
		def activity3 = new Activity(name: 'Cookoff', amountCollected : new BigDecimal('46.87'), donationDate: donationDate.toDate()).save()
		activities << activity3
		def activity4 = new Activity(name: 'BakeOff', amountCollected : new BigDecimal('145.01'), donationDate: donationDate.toDate()).save()
		activities << activity4
		def activity5 = new Activity(name: 'Snacks', amountCollected : new BigDecimal('344.00'), donationDate: donationDate.toDate()).save()
		activities << activity5
		def activity6 = new Activity(name: 'Cookoff', amountCollected : new BigDecimal('99'), donationDate: donationDate.toDate()).save()
		activities << activity6
		
		def total = calcService.sumDonations(activities)
		
		then:
		total.compareTo(new BigDecimal('834.87')) == 0
		
	}
	
	def "calling sumJeanPayments with a wide range of positive numbers"(){
		
		when:
		def jeanPayments = []
		def jeanPayment1 = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal('10.43'), donationDate : donationDate.toDate()).save()
		jeanPayments << jeanPayment1
		def jeanPayment2 = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal('189.56'), donationDate : donationDate.toDate()).save()
		jeanPayments << jeanPayment2
		def jeanPayment3 = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal('46.87'), donationDate : donationDate.toDate()).save()
		jeanPayments << jeanPayment3
		def jeanPayment4 = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal('145.01'), donationDate : donationDate.toDate()).save()
		jeanPayments << jeanPayment4
		def jeanPayment5 = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal('344.00'), donationDate : donationDate.toDate()).save()
		jeanPayments << jeanPayment5
		def jeanPayment6 = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal('99'), donationDate : donationDate.toDate()).save()
		jeanPayments << jeanPayment6
		
		def total = calcService.sumDonations(jeanPayments)
		
		then:
		total.compareTo(new BigDecimal('834.87')) == 0
		
	}
	
//	def "calling sumFoodBankShifts should return the number of meals earned"() {
//		
//		when:
//		def shifts = []
//		def mealFactor1 = new GlobalNumericSetting(name : "Regular", effectiveDate : donationDate.toDate(), mofbShift : true, value : new BigDecimal('33.3')).save()
//		def shift1 = new VolunteerShift(numberOfVolunteers : 10, mealFactor : mealFactor1).save()
//		shifts << shift1
//		
//		def total = calcService.numberOfMealsEarned(shifts)
//		
//		then:
//		total.compareTo(new BigDecimal('333')) == 0
//		
//	}
	

}
