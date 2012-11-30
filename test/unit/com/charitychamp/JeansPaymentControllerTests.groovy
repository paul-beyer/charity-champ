package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(JeansPaymentController)
@Mock(JeansPayment)
class JeansPaymentControllerTests {

    def populateValidParams(params) {
        assert params != null
        
        params["employeeUserId"] = 'harvickk'
		params["payerFirstName"] = 'Kevin'
		params["payerLastName"] = 'Harvick'
		params["amtPaid"] = '86.75'
		params["donationDate"] = new Date()
	
		
		
    }

    void testIndex() {
        controller.index()
        assert "/jeansPayment/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.jeansPaymentInstanceList.size() == 0
        assert model.jeansPaymentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.jeansPaymentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.jeansPaymentInstance != null
        assert view == '/jeansPayment/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/jeansPayment/show/1'
        assert controller.flash.message != null
        assert JeansPayment.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/jeansPayment/list'

        populateValidParams(params)
        def jeansPayment = new JeansPayment(params)

        assert jeansPayment.save() != null

        params.id = jeansPayment.id

        def model = controller.show()

        assert model.jeansPaymentInstance == jeansPayment
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/jeansPayment/list'

        populateValidParams(params)
        def jeansPayment = new JeansPayment(params)

        assert jeansPayment.save() != null

        params.id = jeansPayment.id

        def model = controller.edit()

        assert model.jeansPaymentInstance == jeansPayment
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/jeansPayment/list'

        response.reset()

        populateValidParams(params)
        def jeansPayment = new JeansPayment(params)

        assert jeansPayment.save() != null

        // test invalid parameters in update
        params.id = jeansPayment.id
        params.employeeUserId = ''

        controller.update()

        assert view == "/jeansPayment/edit"
        assert model.jeansPaymentInstance != null

        jeansPayment.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/jeansPayment/show/$jeansPayment.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        jeansPayment.clearErrors()

        populateValidParams(params)
        params.id = jeansPayment.id
        params.version = -1
        controller.update()

        assert view == "/jeansPayment/edit"
        assert model.jeansPaymentInstance != null
        assert model.jeansPaymentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/jeansPayment/list'

        response.reset()

        populateValidParams(params)
        def jeansPayment = new JeansPayment(params)

        assert jeansPayment.save() != null
        assert JeansPayment.count() == 1

        params.id = jeansPayment.id

        controller.delete()

        assert JeansPayment.count() == 0
        assert JeansPayment.get(jeansPayment.id) == null
        assert response.redirectedUrl == '/jeansPayment/list'
    }
}
