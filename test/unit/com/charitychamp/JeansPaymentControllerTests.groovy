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
		params["amountCollected"] = '86.75'
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
