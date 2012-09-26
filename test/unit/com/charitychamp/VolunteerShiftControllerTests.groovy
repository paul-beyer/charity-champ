package com.charitychamp



import java.math.BigDecimal;
import java.util.Date;

import org.junit.*
import grails.test.mixin.*

@TestFor(VolunteerShiftController)
@Mock(VolunteerShift)
class VolunteerShiftControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["dateOfShift"] = new Date()
		params["numberOfVolunteers"] = '12'
		params["mealFactor"] = '33.3'
    }

    void testIndex() {
        controller.index()
        assert "/volunteerShift/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.volunteerShiftInstanceList.size() == 0
        assert model.volunteerShiftInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.volunteerShiftInstance != null
    }

    void testSave() {
        controller.save()

        assert model.volunteerShiftInstance != null
        assert view == '/volunteerShift/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/volunteerShift/show/1'
        assert controller.flash.message != null
        assert VolunteerShift.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/volunteerShift/list'

        populateValidParams(params)
        def volunteerShift = new VolunteerShift(params)

        assert volunteerShift.save() != null

        params.id = volunteerShift.id

        def model = controller.show()

        assert model.volunteerShiftInstance == volunteerShift
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/volunteerShift/list'

        populateValidParams(params)
        def volunteerShift = new VolunteerShift(params)

        assert volunteerShift.save() != null

        params.id = volunteerShift.id

        def model = controller.edit()

        assert model.volunteerShiftInstance == volunteerShift
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/volunteerShift/list'

        response.reset()

        populateValidParams(params)
        def volunteerShift = new VolunteerShift(params)

        assert volunteerShift.save() != null

        // test invalid parameters in update
        params.id = volunteerShift.id
        params.dateOfShift = null

        controller.update()

        assert view == "/volunteerShift/edit"
        assert model.volunteerShiftInstance != null

        volunteerShift.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/volunteerShift/show/$volunteerShift.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        volunteerShift.clearErrors()

        populateValidParams(params)
        params.id = volunteerShift.id
        params.version = -1
        controller.update()

        assert view == "/volunteerShift/edit"
        assert model.volunteerShiftInstance != null
        assert model.volunteerShiftInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/volunteerShift/list'

        response.reset()

        populateValidParams(params)
        def volunteerShift = new VolunteerShift(params)

        assert volunteerShift.save() != null
        assert VolunteerShift.count() == 1

        params.id = volunteerShift.id

        controller.delete()

        assert VolunteerShift.count() == 0
        assert VolunteerShift.get(volunteerShift.id) == null
        assert response.redirectedUrl == '/volunteerShift/list'
    }
}
