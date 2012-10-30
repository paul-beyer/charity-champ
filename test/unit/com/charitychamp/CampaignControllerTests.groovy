package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(CampaignController)
@Mock(Campaign)
class CampaignControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
		params["name"] = "2012"
        params["startDate"] = new Date()
		params["endDate"] = new Date()
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
