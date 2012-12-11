package com.charitychamp



import grails.test.mixin.*
import groovy.mock.interceptor.MockFor

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.*

@TestFor(GroupController)
@Mock([Group, Campaign, Activity, Company, Business, Office, Department, Person, DonationSource, DonationService])
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

//TODO failing for some reason	
//	void testSaveActivityWithEverythingFine(){
//		
//		DateTime startDate = new DateTime(2013, 1 , 2, 0, 0)
//		DateTime endDate = new DateTime(2013, 12 , 30, 0, 0)
//		DateTime donationDate = new DateTime(2013, 8 , 15, 0, 0)
//				 
//		Person person = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save()
//		OrganizationalUnit company = new Company(name: "ACME", leader : person).save()
//		OrganizationalUnit business = new Business(name : "Marketing", leader : person, charityLeader : person, teamNumber : "1411", company : company).save()
//		OrganizationalUnit office = new Office(name : "Enterprise IT", leader : person, charityCaptain : person, business : business).save()
//		OrganizationalUnit department = new Department(name : "Billing", leader : person, charityLieutenant : person, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save()
//		OrganizationalUnit group = new Group(name : 'SOA',  leader : person, department : department).save()
//		Campaign campaign = new Campaign(name: "First", startDate: startDate.toDate(), endDate: endDate.toDate()).save()
//		
//		
//		LocalDate depDate = new LocalDate(2013, 6, 12)
//		params.name = 'Chili Cookoff'
//		params.groupId = group.id
//		params.amountCollected = '100.00'
//		params.depositDate = depDate.toDate()
//		
//		
//		session["campaign"] = campaign.id
//		
//		controller.saveActivity()
//		assert controller.flash.message == 'default.created.message'
//		assert response.redirectedUrl == '/group/activities/1'
//		
//	}
	
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
	
	
}
