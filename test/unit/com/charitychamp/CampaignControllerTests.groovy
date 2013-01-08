package com.charitychamp



import grails.test.mixin.*

import org.joda.time.DateTime
import org.junit.*

@TestFor(CampaignController)
@Mock([Campaign, Company, Activity, DonationSource])
class CampaignControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
		DateTime startDate = new DateTime(2013, 11 , 1, 0, 0)
		DateTime endDate = new DateTime(2013, 11 , 2, 0, 0)
		params["name"] = "2012"
        params["startDate"] = startDate.toDate()
		params["endDate"] = endDate.toDate()
    }

    void testIndex() {
        controller.index()
        assert "/campaign/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.campaignInstanceList.size() == 0
        assert model.campaignInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.campaignInstance != null
    }

    void testSave() {
        controller.save()

        assert model.campaignInstance != null
        assert view == '/campaign/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/campaign/show/1'
        assert controller.flash.message != null
        assert Campaign.count() == 1
    }
	
	void testSaveWithStartAndEndDatesReversed() {
		controller.save()

		assert model.campaignInstance != null
		assert view == '/campaign/create'

		response.reset()

		DateTime startDateThree = new DateTime(2013, 12 , 1, 0, 0)
		DateTime endDateThree = new DateTime(2013, 11 , 30, 0, 0)
		
		populateValidParams(params)
		params["startDate"] = startDateThree.toDate()
		params["endDate"] = endDateThree.toDate()
		controller.save()

		assert view == '/campaign/create'
		assert controller.flash.message == 'campaign.dates.reversed'
		
	}
	
	void testUpdateWithStartAndEndDatesReversed() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/campaign/list'

		response.reset()

		populateValidParams(params)
		def campaign = new Campaign(params)

		assert campaign.save() != null

		DateTime startDate = new DateTime(2013, 11 , 1, 0, 0)
		DateTime endDate = new DateTime(2013, 11 , 2, 0, 0)
		
		// test reversed dates
		params["startDate"] = endDate.toDate()
		params["endDate"] = startDate.toDate()
		
		params.id = campaign.id
	
		controller.update()

		assert view == "/campaign/edit"
		assert model.campaignInstance != null
		assert controller.flash.message == 'campaign.dates.reversed'
	
	}
	
	void testSaveWithStartAndEndDatesOnSameDay() {
		controller.save()

		assert model.campaignInstance != null
		assert view == '/campaign/create'

		response.reset()

		DateTime startDateThree = new DateTime(2013, 12 , 1, 0, 0)
		DateTime endDateThree = new DateTime(2013, 12 , 1, 0, 0)
		
		populateValidParams(params)
		params["startDate"] = startDateThree.toDate()
		params["endDate"] = endDateThree.toDate()
		controller.save()

		assert view == '/campaign/create'
		assert controller.flash.message == 'campaign.dates.reversed'
		
	}
	
	void testUpdateWithStartAndEndDatesOnSameDay() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/campaign/list'

		response.reset()

		populateValidParams(params)
		def campaign = new Campaign(params)

		assert campaign.save() != null

		DateTime startDate = new DateTime(2013, 12 , 1, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 1, 0, 0)
		
	
		params["startDate"] = startDate.toDate()
		params["endDate"] = endDate.toDate()
		
		params.id = campaign.id
	
		controller.update()

		assert view == "/campaign/edit"
		assert model.campaignInstance != null
		assert controller.flash.message == 'campaign.dates.reversed'

	
	}
	
	void testSaveWithOverlappingDates() {
		controller.save()

		assert model.campaignInstance != null
		assert view == '/campaign/create'

		response.reset()
		
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
		
		DateTime startDateThree = new DateTime(2012, 11 , 15, 0, 0)
		DateTime endDateThree = new DateTime(2013, 12 , 31, 0, 0)
		
		populateValidParams(params)
		params["startDate"] = startDateThree.toDate()
		params["endDate"] = endDateThree.toDate()
		controller.save()

		assert view == '/campaign/create'
		assert controller.flash.message == 'campaign.dates.overlap'
		
	}
	
	void testUpdateWithOverlappingDates() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/campaign/list'

		response.reset()

		populateValidParams(params)
		def campaign = new Campaign(params)

		assert campaign.save() != null
		
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
			
		DateTime startDate = new DateTime(2012, 11 , 15, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 31, 0, 0)
		
	
		params["startDate"] = startDate.toDate()
		params["endDate"] = endDate.toDate()
		
		params.id = campaign.id
	
		controller.update()

		assert view == "/campaign/edit"
		assert model.campaignInstance != null
		assert controller.flash.message == 'campaign.dates.overlap'

	
	}
	
	void testSaveWithOverlappingDatesOnSameDay() {
		controller.save()

		assert model.campaignInstance != null
		assert view == '/campaign/create'

		response.reset()
		
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
		
		DateTime startDateThree = new DateTime(2012, 12 , 31, 0, 0)
		DateTime endDateThree = new DateTime(2013, 12 , 31, 0, 0)
		
		populateValidParams(params)
		params["startDate"] = startDateThree.toDate()
		params["endDate"] = endDateThree.toDate()
		controller.save()

		assert view == '/campaign/create'
		assert controller.flash.message == 'campaign.dates.overlap'
		
	}
	
	void testUpdateWithOverlappingDatesOnSameDay() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/campaign/list'

		response.reset()

		populateValidParams(params)
		def campaign = new Campaign(params)

		assert campaign.save() != null
		
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
			
		DateTime startDate = new DateTime(2012, 12 , 31, 0, 0)
		DateTime endDate = new DateTime(2013, 12 , 31, 0, 0)
		
	
		params["startDate"] = startDate.toDate()
		params["endDate"] = endDate.toDate()
		
		params.id = campaign.id
	
		controller.update()

		assert view == "/campaign/edit"
		assert model.campaignInstance != null
		assert controller.flash.message == 'campaign.dates.overlap'

	
	}
	
	void testUpdateWithDonationsDroppingOutOfCampaign() {
		
		DateTime donationDate = new DateTime(2012, 12 , 28, 0, 0)
		def orgUnit = new Company(name: "Some Company")
		def activity = new Activity(name : "Chili Cookoff", amountCollected : new BigDecimal(100), donationDate : donationDate.toDate()).save()
		def donationSource = new DonationSource(donation : activity, orgUnit : orgUnit).save()
		
				
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
		firstCampaign.addToDonationSources(donationSource).save()
		donationSource.campaign = firstCampaign
		donationSource.save()
			
		DateTime startDate = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDate = new DateTime(2012, 12 , 27, 0, 0)
		
	
		params["startDate"] = startDate.toDate()
		params["endDate"] = endDate.toDate()
		
		params.id = firstCampaign.id
	
		controller.update()

		assert view == "/campaign/edit"
		assert model.campaignInstance != null
		assert controller.flash.message == 'campaign.donations.are.orphaned'

	
	}
	
	void testDeleteWithDonationsDroppingOutOfCampaign() {
		DateTime donationDate = new DateTime(2012, 12 , 28, 0, 0)
		def orgUnit = new Company(name: "Some Company")
		def activity = new Activity(name : "Chili Cookoff", amountCollected : new BigDecimal(100), donationDate : donationDate.toDate()).save()
		def donationSource = new DonationSource(donation : activity, orgUnit : orgUnit).save()
		
				
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
		firstCampaign.addToDonationSources(donationSource).save()
		donationSource.campaign = firstCampaign
		donationSource.save()
			
				
		params.id = firstCampaign.id
		
		controller.delete()

		assert Campaign.count() == 1
		assert Campaign.get(firstCampaign.id) != null
	    assert response.redirectedUrl == "/campaign/show/$firstCampaign.id"
	}
	
	void testUpdateWithDonationsNotDroppingOutOfCampaign() {
		
		DateTime donationDate = new DateTime(2012, 12 , 20, 0, 0)
		def orgUnit = new Company(name: "Some Company")
		def activity = new Activity(name : "Chili Cookoff", amountCollected : new BigDecimal(100), donationDate : donationDate.toDate()).save()
		def donationSource = new DonationSource(donation : activity, orgUnit : orgUnit).save()
		
				
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
		firstCampaign.addToDonationSources(donationSource).save()
		donationSource.campaign = firstCampaign
		donationSource.save()
			
		DateTime startDate = new DateTime(2012, 1, 1, 0, 0)
		DateTime endDate = new DateTime(2012, 12 , 27, 0, 0)
		
	
		params["startDate"] = startDate.toDate()
		params["endDate"] = endDate.toDate()
		
		params.id = firstCampaign.id
	
		controller.update()

		assert response.redirectedUrl == "/campaign/show/$firstCampaign.id"
        assert flash.message != null

	
	}
	
	void testSaveWithImmediateNextDayStart() {
		controller.save()

		assert model.campaignInstance != null
		assert view == '/campaign/create'

		response.reset()
		
		DateTime startDateOne = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2012, 12 , 31, 0, 0)
		
		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
		assert firstCampaign != null
		
		DateTime startDateThree = new DateTime(2013, 1 , 1, 0, 0)
		DateTime endDateThree = new DateTime(2013, 12 , 31, 0, 0)
		
		populateValidParams(params)
		params["startDate"] = startDateThree.toDate()
		params["endDate"] = endDateThree.toDate()
		controller.save()

		assert response.redirectedUrl == '/campaign/show/2'
        assert controller.flash.message != null
		
	}
	
// Had to comment this test because the dates were getting updated on the object under test
// before I actually called the Update method
//
//	void testUpdateWithImmediateNextDayStart() {
//		controller.update()
//
//		assert flash.message != null
//		assert response.redirectedUrl == '/campaign/list'
//
//		response.reset()
//
//		populateValidParams(params)
//		def campaign = new Campaign(params)
//
//		assert campaign.save() != null
//		println campaign.id
//		
//		DateTime startDateOne = new DateTime(2014, 1 , 1, 0, 0)
//		DateTime endDateOne = new DateTime(2014, 12 , 31, 0, 0)
//		
//		def firstCampaign = new Campaign(name : "First Campaign", startDate:startDateOne.toDate(), endDate:endDateOne.toDate()).save()
//		assert firstCampaign != null
//				
//		DateTime startDate = new DateTime(2013, 11 , 3, 0, 0)
//		DateTime endDate = new DateTime(2013, 11 , 30, 0, 0)
//		
//	
//		params["startDate"] = startDate.toDate()
//		params["endDate"] = endDate.toDate()
//		
//		params.id = firstCampaign.id
//	
//		controller.update()
//
//		println view
//		println controller.flash.message
//		assert response.redirectedUrl == '/campaign/show/2'
//		assert controller.flash.message != null
//	
//	
//	}

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/campaign/list'

        populateValidParams(params)
        def campaign = new Campaign(params)

        assert campaign.save() != null

        params.id = campaign.id

        def model = controller.show()

        assert model.campaignInstance == campaign
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/campaign/list'

        populateValidParams(params)
        def campaign = new Campaign(params)

        assert campaign.save() != null

        params.id = campaign.id

        def model = controller.edit()

        assert model.campaignInstance == campaign
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/campaign/list'

        response.reset()

        populateValidParams(params)
        def campaign = new Campaign(params)

        assert campaign.save() != null

        // test invalid parameters in update
        params.id = campaign.id
        params.startDate = null
		params.endDate = null

        controller.update()

        assert view == "/campaign/edit"
        assert model.campaignInstance != null

        campaign.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/campaign/show/$campaign.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        campaign.clearErrors()

        populateValidParams(params)
        params.id = campaign.id
        params.version = -1
        controller.update()

        assert view == "/campaign/edit"
        assert model.campaignInstance != null
        assert model.campaignInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/campaign/list'

        response.reset()

        populateValidParams(params)
        def campaign = new Campaign(params)

        assert campaign.save() != null
        assert Campaign.count() == 1

        params.id = campaign.id

        controller.delete()

        assert Campaign.count() == 0
        assert Campaign.get(campaign.id) == null
        assert response.redirectedUrl == '/campaign/list'
    }
}
