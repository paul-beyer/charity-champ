package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(BusinessController)
@Mock(Business)
class BusinessControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["name"] = 'IT'
		params["executive"] = new Person(userId:'gambit', firstName: "Remy", lastName: "LeBeau")
		params["charityLeader"] = new Person(userId:'biffle', firstName: "Greg", lastName: "Biffle")
		params["teamNumber"] = "1234"
		
    }

    void testIndex() {
        controller.index()
        assert "/business/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.businessInstanceList.size() == 0
        assert model.businessInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.businessInstance != null
    }

    void testSave() {
        controller.save()

        assert model.businessInstance != null
        assert view == '/business/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/business/show/1'
        assert controller.flash.message != null
        assert Business.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/business/list'

        populateValidParams(params)
        def business = new Business(params)

        assert business.save() != null

        params.id = business.id

        def model = controller.show()

        assert model.businessInstance == business
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/business/list'

        populateValidParams(params)
        def business = new Business(params)

        assert business.save() != null

        params.id = business.id

        def model = controller.edit()

        assert model.businessInstance == business
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/business/list'

        response.reset()

        populateValidParams(params)
        def business = new Business(params)

        assert business.save() != null

        // test invalid parameters in update
        params.id = business.id
        params.teamNumber = ''
		
        controller.update()

        assert view == "/business/edit"
        assert model.businessInstance != null

        business.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/business/show/$business.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        business.clearErrors()

        populateValidParams(params)
        params.id = business.id
        params.version = -1
        controller.update()

        assert view == "/business/edit"
        assert model.businessInstance != null
        assert model.businessInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/business/list'

        response.reset()

        populateValidParams(params)
        def business = new Business(params)

        assert business.save() != null
        assert Business.count() == 1

        params.id = business.id

        controller.delete()

        assert Business.count() == 0
        assert Business.get(business.id) == null
        assert response.redirectedUrl == '/business/list'
    }
}
