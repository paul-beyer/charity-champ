package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(OfficeController)
@Mock(Office)
class OfficeControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["name"] = 'Enterprise Apps'
		params["officer"] = new Person(userId:'gambit', firstName: "Remy", lastName: "LeBeau")
		params["charityCaptain"] = new Person(userId:'biffle', firstName: "Greg", lastName: "Biffle")
		
    }

    void testIndex() {
        controller.index()
        assert "/office/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.officeInstanceList.size() == 0
        assert model.officeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.officeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.officeInstance != null
        assert view == '/office/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/office/show/1'
        assert controller.flash.message != null
        assert Office.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/office/list'

        populateValidParams(params)
        def office = new Office(params)

        assert office.save() != null

        params.id = office.id

        def model = controller.show()

        assert model.officeInstance == office
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/office/list'

        populateValidParams(params)
        def office = new Office(params)

        assert office.save() != null

        params.id = office.id

        def model = controller.edit()

        assert model.officeInstance == office
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/office/list'

        response.reset()

        populateValidParams(params)
        def office = new Office(params)

        assert office.save() != null

        // test invalid parameters in update
        params.id = office.id
        params.name = ''

        controller.update()

        assert view == "/office/edit"
        assert model.officeInstance != null

        office.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/office/show/$office.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        office.clearErrors()

        populateValidParams(params)
        params.id = office.id
        params.version = -1
        controller.update()

        assert view == "/office/edit"
        assert model.officeInstance != null
        assert model.officeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/office/list'

        response.reset()

        populateValidParams(params)
        def office = new Office(params)

        assert office.save() != null
        assert Office.count() == 1

        params.id = office.id

        controller.delete()

        assert Office.count() == 0
        assert Office.get(office.id) == null
        assert response.redirectedUrl == '/office/list'
    }
}
