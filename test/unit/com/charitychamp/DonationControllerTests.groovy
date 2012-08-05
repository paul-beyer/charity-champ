package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(DonationController)
@Mock(Donation)
class DonationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["amount"] = '1'
		params["purpose"] = 'Operation Feed donation'
    }

    void testIndex() {
        controller.index()
        assert "/donation/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.donationInstanceList.size() == 0
        assert model.donationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.donationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.donationInstance != null
        assert view == '/donation/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/donation/show/1'
        assert controller.flash.message != null
        assert Donation.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/donation/list'

        populateValidParams(params)
        def donation = new Donation(params)

        assert donation.save() != null

        params.id = donation.id

        def model = controller.show()

        assert model.donationInstance == donation
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/donation/list'

        populateValidParams(params)
        def donation = new Donation(params)

        assert donation.save() != null

        params.id = donation.id

        def model = controller.edit()

        assert model.donationInstance == donation
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/donation/list'

        response.reset()

        populateValidParams(params)
        def donation = new Donation(params)

        assert donation.save() != null

        // test invalid parameters in update
        params.id = donation.id
        //TODO: add invalid values to params object
		params["amount"] = '.99'

        controller.update()

        assert view == "/donation/edit"
        assert model.donationInstance != null

        donation.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/donation/show/$donation.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        donation.clearErrors()

        populateValidParams(params)
        params.id = donation.id
        params.version = -1
        controller.update()

        assert view == "/donation/edit"
        assert model.donationInstance != null
        assert model.donationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/donation/list'

        response.reset()

        populateValidParams(params)
        def donation = new Donation(params)

        assert donation.save() != null
        assert Donation.count() == 1

        params.id = donation.id

        controller.delete()

        assert Donation.count() == 0
        assert Donation.get(donation.id) == null
        assert response.redirectedUrl == '/donation/list'
    }
}
