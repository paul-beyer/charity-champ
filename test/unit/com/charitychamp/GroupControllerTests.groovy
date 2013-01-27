package com.charitychamp



import java.math.BigDecimal;

import grails.test.mixin.*
import groovy.mock.interceptor.MockFor

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.*

@TestFor(GroupController)
@Mock([Group, Campaign, Activity, Company, Business, Office, Department, Person, DonationSource, DonationService, GlobalNumericSetting, VolunteerShift, JeansPayment, StringList])
class GroupControllerTests {
	
	
	void setUp(){
		
	}

    def populateValidParams(params) {
        assert params != null
        params["name"] = 'SOA'
		params["leader"] = new Person(userId:'gambit', firstName: "Remy", lastName: "LeBeau")
		params["department"] = new Department(name : "Billing")
		
    }

    void testIndex() {
        controller.index()
        assert "/group/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.groupInstanceList.size() == 0
        assert model.groupInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.groupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.groupInstance != null
        assert view == '/group/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/group/show/1'
        assert controller.flash.message != null
        assert Group.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/group/list'

        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null

        params.id = group.id

        def model = controller.show()

        assert model.groupInstance == group
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/group/list'

        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null

        params.id = group.id

        def model = controller.edit()

        assert model.groupInstance == group
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/group/list'

        response.reset()

        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null

        // test invalid parameters in update
        params.id = group.id
        params.name = ''

        controller.update()

        assert view == "/group/edit"
        assert model.groupInstance != null

        group.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/group/show/$group.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        group.clearErrors()

        populateValidParams(params)
        params.id = group.id
        params.version = -1
        controller.update()

        assert view == "/group/edit"
        assert model.groupInstance != null
        assert model.groupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/group/list'

        response.reset()

        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null
        assert Group.count() == 1

        params.id = group.id

        controller.delete()

        assert Group.count() == 0
        assert Group.get(group.id) == null
        assert response.redirectedUrl == '/group/list'
    }
	
	void testDeleteActivityWithNoIdPassedIn(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
		
		
		controller.deleteActivity(group.id, null)
		assert flash.message != null
        assert  response.redirectedUrl == '/group/activities/1'
	}
	
	void testDeleteActivityWithGroupNotFound(){
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
		
		
		controller.deleteActivity(null, activity.id)
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/home/home'
	}
	
	void testSuccessfullyDeleteActivity(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "barkleyc", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save(flush : true)
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush : true)
		DonationSource donation = new DonationSource(donation: activity, orgUnit : group).save(flush : true)
		campaign.addToDonationSources(donation).save(flush : true)
		
		session["campaign"] = campaign.id
		
		controller.deleteActivity(group.id, activity.id)
		assert DonationSource.count() == 0
        assert Activity.count() == 0
		assert Group.get(group.id) != null
		assert campaign.donationSources.size() == 0
        assert response.redirectedUrl == '/group/activities/1'
		
	}
	
	void testDeleteVolunteerShiftWithNoIdPassedIn(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		controller.deleteFoodBankShift(group.id, null)
		assert flash.message != null
		assert  response.redirectedUrl == '/group/foodBankShifts/1'
	}
	
	void testDeleteVolunteerShiftWithNoGroupIdPassedIn(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		controller.deleteFoodBankShift(null, volunteerShift.id)
		assert flash.message != null
		assert  response.redirectedUrl == '/home/home'
	}
	
	void testSuccessfullyDeleteVolunteerShift(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "barkleyc", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save(flush : true)
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		DonationSource donation = new DonationSource(donation: volunteerShift, orgUnit : group).save(flush : true)
		campaign.addToDonationSources(donation).save(flush : true)
		
		session["campaign"] = campaign.id
		
		controller.deleteFoodBankShift(group.id, volunteerShift.id)
		assert DonationSource.count() == 0
		assert VolunteerShift.count() == 0
		assert Group.get(group.id) != null
		assert campaign.donationSources.size() == 0
		assert response.redirectedUrl == '/group/foodBankShifts/1'
		
	}
	
		
	void testSaveActivityWithGarbageInputAmount(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
	
	
//		DonationSource donationSource = new DonationSource(donation : activity, orgUnit : group).save()
//		List donations = new ArrayList()
//		donations << donationSource
//		campaign.donationSources = donations
//		campaign.save()
		
		params.groupId = group.id
		params.amountCollected = 'AAAA'
		
		session["campaign"] = campaign.id
		
		controller.saveActivity()
		assert controller.flash.message == 'activity.amount.collected.parse.exception'
		assert model.groupInstance != null
		assert model.activityInstance != null
		assert view == '/group/addActivity'
		
	}
	
	void testSaveActivityWithDepositDateOutsideOfCampaign(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
	
	
//		DonationSource donationSource = new DonationSource(donation : activity, orgUnit : group).save()
//		List donations = new ArrayList()
//		donations << donationSource
//		campaign.donationSources = donations
//		campaign.save()
		
		LocalDate depDate = new LocalDate(2014, 6, 12)
		params.groupId = group.id
		params.amountCollected = '100.00'
		params.depositDate = depDate.toDate()
		
		
		session["campaign"] = campaign.id
		
		controller.saveActivity()
		controller.flash.message == 'donation.date.not.in.valid.campaign'
		assert model.groupInstance != null
		assert model.activityInstance != null
		assert view == '/group/addActivity'
		
	}


	
	void testUpdatingActivityWithGroupNotFound(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
		
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.name = 'Chili Cookoff'
		params.amountCollected = '100.00'
		params.depositDate = depDate.toDate()
		
		controller.updateActivity(0, activity.id, activity.version)
		
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/home/home'
		
	}
	
	void testUpdatingVolunteerShiftWithGroupNotFound(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		params.numberOfVolunteers = 10
		params.id = null
		params.shiftId = volunteerShift.id
		params.shiftVersion = volunteerShift.version
		params.comments = 'Some Comment'
		params.donationDate = donationDate.toDate()
		params.mealFactorId = global.id
		session["campaign"] = campaign.id
	
		
		controller.updateFoodBankShift()
		
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/home/home'
		
	}
	
	void testUpdatingActivityWithActivityNotFound(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save()
		
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.name = 'Chili Cookoff'
		params.amountCollected = '100.00'
		params.depositDate = depDate.toDate()
		
		controller.updateActivity(group.id, 0, activity.version)
		
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/group/activities/1'
		
	}
	
	void testUpdatingJeanPaymentWithJeanPaymentNotFound(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		
				
		controller.updateJeanPayment(group.id, 0, jeanPayment.version)
		
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/group/jeanPayments/1'
		
	}
	
	void testUpdatingVolunteerShiftWithNoVolunteerShiftFound(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		params.numberOfVolunteers = 10
		params.id = group.id
		params.shiftId = null
		params.shiftVersion = volunteerShift.version
		params.comments = 'Some Comment'
		params.donationDate = donationDate.toDate()
		params.mealFactorId = global.id
		session["campaign"] = campaign.id
	
		
		controller.updateFoodBankShift()
		
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/group/foodBankShifts/1'
		
	}
	
	void testUpdatingActivityWithBadVersion(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush:true)
		
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.name = 'Chili Cookoff'
		params.amountCollected = '100.00'
		params.depositDate = depDate.toDate()
		session["campaign"] = campaign.id
		controller.updateActivity(group.id, activity.id, -1)
		
		assert model.groupInstance != null
		assert model.activityInstance != null
		assert view == '/group/editActivity'
		
	}
	
	void testUpdatingJeanPaymentWithBadVersion(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		
	params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = 'Yeah right'
		params.dateOfPayment = donationDate.toDate()
		params.jeanPaymentId = jeanPayment.id
		params.jeanPaymentVersion = -1
		params.id = group.id
		session["campaign"] = campaign.id
	
		controller.updateJeanPayment()
		
		assert model.groupInstance != null
		assert model.jeansPaymentInstance != null
		assert view == '/group/editJeanPayments'
		
	}
	
	void testUpdatingJeanPaymentWithDonationDateThatDoesNotFallInAValidCampaign(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		LocalDate depDate = new LocalDate(2014, 6, 12)
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = '100.00'
		params.dateOfPayment = depDate.toDate()
		params.jeanPaymentId = jeanPayment.id
		params.jeanPaymentVersion = jeanPayment.version
		params.id = group.id
		session["campaign"] = campaign.id
		controller.updateJeanPayment()
	
		assert model.groupInstance != null
		assert model.jeansPaymentInstance != null
		assert view == '/group/editJeanPayments'
		
	}
	
	void testUpdatingJeanPayment(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
	
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = '100.00'
		params.dateOfPayment = depDate.toDate()
		params.jeanPaymentId = jeanPayment.id
		params.jeanPaymentVersion = jeanPayment.version
		params.id = group.id
		session["campaign"] = campaign.id
		
		controller.updateJeanPayment()
		
		assert response.redirectedUrl == "/group/jeanPayments/$group.id"
		assert flash.message != null
		
	}
	
	void testDeleteJeanPaymentWithGroupNotFound(){
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = '100.00'
		params.dateOfPayment = depDate.toDate()
		params.jeanPaymentId = jeanPayment.id
		params.jeanPaymentVersion = jeanPayment.version
		params.id = null
		session["campaign"] = campaign.id
		
		controller.deleteJeanPayment()
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/home/home'
	}
	
	void testDeleteJeanPaymentWithNoIdPassedIn(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = '100.00'
		params.dateOfPayment = depDate.toDate()
		params.jeanPaymentId = null
		params.jeanPaymentVersion = jeanPayment.version
		params.id = group.id 
		session["campaign"] = campaign.id
		
		controller.deleteJeanPayment()
		assert flash.message != null
		assert  response.redirectedUrl == '/group/jeanPayments/1'
	}
	
	void testSuccessfullyDeleteJeanPayment(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "barkleyc", firstName : "Charles", lastName : "Barkley", personTitle : "CEO", email : "breesd@gmail.com").save(flush : true)
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save(flush : true)
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save(flush : true)
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save(flush : true)
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush : true)
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save(flush : true)
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save(flush : true)
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		DonationSource donation = new DonationSource(donation: jeanPayment, orgUnit : group).save(flush : true)
		campaign.addToDonationSources(donation).save(flush : true)
		
		session["campaign"] = campaign.id
		
		controller.deleteJeanPayment(group.id, jeanPayment.id)
		assert DonationSource.count() == 0
		assert JeansPayment.count() == 0
		assert Group.get(group.id) != null
		assert campaign.donationSources.size() == 0
		assert response.redirectedUrl == '/group/jeanPayments/1'
		
	}
	
	void testUpdatingJeanPaymentWithGroupNotFound(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = '100.00'
		params.dateOfPayment = depDate.toDate()
		params.jeanPaymentId = jeanPayment.id
		params.jeanPaymentVersion = jeanPayment.version
		params.id = 0
		session["campaign"] = campaign.id
		
		controller.updateJeanPayment()
		
		assert controller.flash.message == 'default.not.found.message'
		assert response.redirectedUrl == '/home/home'
		
	}
	
	void testUpdatingVolunteerShiftWithBadVersion(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		params.numberOfVolunteers = 10
		params.id = group.id
		params.shiftId = volunteerShift.id
		params.shiftVersion = -1
		params.comments = 'Some Comment'
		params.donationDate = donationDate.toDate()
		params.mealFactorId = global.id
		session["campaign"] = campaign.id
	
		
		controller.updateFoodBankShift()
		
		assert model.groupInstance != null
		assert model.volunteerShiftInstance != null
		assert view == '/group/editFoodBankShift'
		
	}
	
	void testUpdatingActivityWithBadAmount(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush:true)
		
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.name = 'Chili Cookoff'
		params.amountCollected = 'A'
		params.depositDate = depDate.toDate()
		session["campaign"] = campaign.id
		controller.updateActivity(group.id, activity.id, activity.version)
		
		assert model.groupInstance != null
		assert model.activityInstance != null
		assert view == '/group/editActivity'
		
	}
	
	void testUpdatingJeanPaymentWithBadAmount(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation jeanPayment = new JeansPayment(employeeUserId: 'beyerp', payerFirstName : 'Paul', payerLastName: 'Beyer', amountCollected : new BigDecimal(100.00), donationDate : donationDate.toDate()).save()
	
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.amtPaid = 'Yeah right'
		params.dateOfPayment = donationDate.toDate()
		params.jeanPaymentId = jeanPayment.id
		params.jeanPaymentVersion = jeanPayment.version
		params.id = group.id
		session["campaign"] = campaign.id
	
		controller.updateJeanPayment()
		
		assert model.groupInstance != null
		assert model.jeansPaymentInstance != null
		assert view == '/group/editJeanPayments'
		
	}
	
	
	void testUpdatingVolunteerShiftWithBadMealFactor(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		params.numberOfVolunteers = 10
		params.id = group.id
		params.shiftId = volunteerShift.id
		params.shiftVersion = volunteerShift.version
		params.comments = 'Some Comment'
		params.donationDate = donationDate.toDate()
		params.mealFactorId = null
		session["campaign"] = campaign.id
	
		
		controller.updateFoodBankShift()
		
		assert model.groupInstance != null
		assert model.volunteerShiftInstance != null
		assert view == '/group/editFoodBankShift'
		
	}
	
	void testUpdatingActivityWithDonationDateThatDoesNotFallInAValidCampaign(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush:true)
		
		LocalDate depDate = new LocalDate(2014, 6, 12)
		params.name = 'Chili Cookoff'
		params.amountCollected = '100.00'
		params.depositDate = depDate.toDate()
		params.campaignId = campaign.id 
		session["campaign"] = campaign.id
		controller.updateActivity(group.id, activity.id, activity.version)
		
		assert model.groupInstance != null
		assert model.activityInstance != null
		assert view == '/group/editActivity'
		
	}
	
	void testUpdatingVolunteerShiftWithDonationDateThatDoesNotFallInAValidCampaign(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		LocalDate depDate = new LocalDate(2014, 6, 12)
		params.numberOfVolunteers = 10
		params.id = group.id
		params.shiftId = volunteerShift.id
		params.shiftVersion = volunteerShift.version
		params.comments = 'Some Comment'
		params.donationDate = depDate.toDate()
		params.mealFactorId = global.id
		session["campaign"] = campaign.id
	
		
		controller.updateFoodBankShift()
		
		assert model.groupInstance != null
		assert model.volunteerShiftInstance != null
		assert view == '/group/editFoodBankShift'
		
	}
	
	void testUpdatingActivityWithEverythingGood(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
	
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		Donation activity = new Activity(name: 'BakeOff', amountCollected : new BigDecimal(100.00), donationDate: donationDate.toDate()).save(flush:true)
		
		LocalDate depDate = new LocalDate(2013, 6, 12)
		params.name = 'Chili Cookoff'
		params.amountCollected = '100.00'
		params.depositDate = depDate.toDate()
		session["campaign"] = campaign.id
		controller.updateActivity(group.id, activity.id, activity.version)
		
		assert response.redirectedUrl == "/group/activities/$group.id"
        assert flash.message != null
		
	}
	
	void testUpdatingVolunteerShiftWithEverthingGood(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
		Donation volunteerShift = new VolunteerShift(numberOfVolunteers: 10, mealFactor : global, donationDate: donationDate.toDate()).save()
		
		
		params.numberOfVolunteers = 10
		params.id = group.id
		params.shiftId = volunteerShift.id
		params.shiftVersion = volunteerShift.version
		params.comments = 'Some Comment'
		params.donationDate = donationDate.toDate()
		params.mealFactorId = global.id
		session["campaign"] = campaign.id
	
		
		controller.updateFoodBankShift()
		
		assert response.redirectedUrl == "/group/foodBankShifts/$group.id"
        assert flash.message != null
		
		
	}
	
	void testSaveFoodBankShift(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime shiftDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
				
		params.groupId = group.id
		params.numberOfVolunteers = 10
		params.comments = 'Some Comment'
		params.donationDate = shiftDate.toDate()
		params.mealFactorId = global.id
		session["campaign"] = campaign.id
		
		controller.saveFoodBankShift()
		
	    assert controller.flash.message != null
		assert response.redirectedUrl == '/group/foodBankShifts/1'
		
	}
	
	void testSaveActivityWithEverythingFine(){
	
			DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
			DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
			DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
	
			Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
			OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
			OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
			OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
			OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
			OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
			Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
	
	
			params.name = 'Chili Cookoff'
			params.groupId = group.id
			params.amountCollected = '100.00'
			params.depositDate = donationDate.toDate()
	
	
			session["campaign"] = campaign.id
	
			controller.saveActivity()
			assert controller.flash.message == 'default.created.message'
			assert response.redirectedUrl == '/group/activities/1'
	
		}
	
	void testSaveJeanPayment(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime shiftDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
	
		params.groupId = group.id
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.payerPhone = '555-5555'
		params.payerEmail = 'somebody@gmail.com'
		params.dateOfPayment = shiftDate.toDate()
		params.amtPaid = '89.00'
		session["campaign"] = campaign.id
		
		controller.saveJeanPayment()
		
		assert controller.flash.message != null
		assert response.redirectedUrl == '/group/jeanPayments/1'
		
	}
	
	void testSaveJeanPaymentWithBadInput(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime shiftDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
	
		params.groupId = group.id
		params.employeeUserId = ''
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.payerPhone = '555-5555'
		params.payerEmail = 'somebody@gmail.com'
		params.dateOfPayment = shiftDate.toDate()
		params.amtPaid = '89.00'
		session["campaign"] = campaign.id
		
		controller.saveJeanPayment()
	
		assert view == '/group/addJeanPayment'
		
	}
	
	void testSaveJeanPaymentWithDateNotInCurrentCampaign(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime shiftDate = new DateTime(2014, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
	
		params.groupId = group.id
		params.employeeUserId = 'beyerp'
		params.payerFirstName = 'Paul'
		params.payerLastName = 'Beyer'
		params.payerPhone = '555-5555'
		params.payerEmail = 'somebody@gmail.com'
		params.dateOfPayment = shiftDate.toDate()
		params.amtPaid = '89.00'
		session["campaign"] = campaign.id
		
		controller.saveJeanPayment()
	
		assert controller.flash.message != null
		assert view == '/group/addJeanPayment'
		
	}
	
	void testSaveFoodBankShiftWithNoMealFactorSelected(){
		
		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
		DateTime shiftDate = new DateTime(2013, 8 , 15, 0, 0)
				 
		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
		GlobalNumericSetting global = new GlobalNumericSetting(name : 'Executive Shift', effectiveDate : startDate.toDate(), value : new BigDecimal(33), mofbShift : true).save()
				
		params.groupId = group.id
		params.numberOfVolunteers = 10
		params.comments = 'Some Comment'
		params.donationDate = shiftDate.toDate()
		params.mealFactorId = null
		session["campaign"] = campaign.id
		
		controller.saveFoodBankShift()
		
		assert controller.flash.message != null
		assert view == '/group/addFoodBankShift'
		
	}
	
	
}
