package com.charitychamp



import grails.test.mixin.*
import groovy.mock.interceptor.MockFor

import org.joda.time.LocalDate
import org.junit.*

@TestFor(GlobalNumericSettingController)
@Mock(GlobalNumericSetting)
class GlobalNumericSettingControllerTests {

    def populateValidParams(params) {
        assert params != null
    
        params["name"] = 'amountPerEmployee'
		params["effectiveDate"] = new Date()
		params["value"] = '56.75'
		params["mofbShift"] = true
		
    }

    void testIndex() {
        controller.index()
        assert "/globalNumericSetting/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.globalNumericSettingInstanceList.size() == 0
        assert model.globalNumericSettingInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.globalNumericSettingInstance != null
    }
	
	void testCreateEmployeeGoal(){
		
		def model = controller.createEmployeeGoal()
		 assert view == '/globalNumericSetting/createEmployeeGoal'
	}
	
	void testCreateMofbShiftValue(){
		
		def model = controller.createMofbShiftValue()
		 assert view == '/globalNumericSetting/createMofbShiftValue'
	}
	
	void testCreateMealADollarBuys() {
		
		def model = controller.createMealADollarBuys()
		 assert view == '/globalNumericSetting/createMealsADollarBuys'
	}

    void testSaveMofbShift() {
		
		def result = true
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.validateFoodBankShift(1..2) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
        controller.saveMofbShift()

        assert model.globalNumericSettingInstance != null
        assert view == '/globalNumericSetting/createMofbShiftValue'

        response.reset()

        populateValidParams(params)
        controller.saveMofbShift()

        assert response.redirectedUrl == "/globalNumericSetting/mofbShiftValue"
        assert controller.flash.message != null
        assert GlobalNumericSetting.count() == 1
    }
	
	void testSaveMofbShiftWithBadNumericInput(){
		
		def result = true
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.validateFoodBankShift(1..2) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		controller.saveMofbShift()
		
		assert model.globalNumericSettingInstance != null
		assert view == '/globalNumericSetting/createMofbShiftValue'
		
		response.reset()
		
		populateValidParams(params)
		params["value"] = 'AAAA'
		controller.saveMofbShift()
		
		assert view ==  '/globalNumericSetting/createMofbShiftValue'
		assert controller.flash.message != null
		assert GlobalNumericSetting.count() == 0
		
	}
	
	void testSaveEmployeeGoal() {
		
		def result = false
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..2) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		controller.saveEmployeeGoal()

		assert model.globalNumericSettingInstance != null
		assert view == '/globalNumericSetting/createEmployeeGoal'

		response.reset()

		populateValidParams(params)
		controller.saveEmployeeGoal()

		assert response.redirectedUrl == "/globalNumericSetting/goalPerEmployee"
		assert controller.flash.message != null
		assert GlobalNumericSetting.count() == 1
	}
	
	void testSaveEmployeeGoalWithBadNumericInput(){
		
		def result = false
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..2) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		controller.saveEmployeeGoal()
		
		assert model.globalNumericSettingInstance != null
		assert view == '/globalNumericSetting/createEmployeeGoal'
		
		response.reset()
		
		populateValidParams(params)
		params["value"] = 'AAAA'
		controller.saveEmployeeGoal()
		
		assert view ==  '/globalNumericSetting/createEmployeeGoal'
		assert controller.flash.message != null
		assert GlobalNumericSetting.count() == 0
		
	}
	
	void testSaveMealsDollarBuys() {
		
		def result = false
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..1) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		populateValidParams(params)
		params["mofbShift"] = false
		controller.saveMealsDollarBuys()

		assert response.redirectedUrl == "/globalNumericSetting/mealsADollarBuys"
		assert controller.flash.message != null
		assert GlobalNumericSetting.count() == 1
	}
	
	void testSaveMealsDollarBuysWithAnotherAlreadySavedWithThatDate() {
			
		def result = true
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..1) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
	
		LocalDate firstDate = new LocalDate(2012, 1, 1)
		def firstMealADollarBuys = new GlobalNumericSetting(name : 'Meals a Dollar Buys', effectiveDate: firstDate.toDate(), value : new BigDecimal('33'), mofbShift : false).save()
			
		
		populateValidParams(params)
		params["name"] = 'Meals a Dollar Buys'
		params["effectiveDate"] = firstDate.toDate()
		params["mofbShift"] = false
	
		controller.saveMealsDollarBuys()

		assert model.globalNumericSettingInstance != null
		assert view == '/globalNumericSetting/createMealsADollarBuys'
		assert GlobalNumericSetting.count() == 1	
	}
		
	void testSaveMealsDollarBuysWithBadNumericInput(){
		
		def result = false
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..2) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		controller.saveMealsDollarBuys()
		
		assert model.globalNumericSettingInstance != null
		assert view == '/globalNumericSetting/createMealsADollarBuys'
		
		response.reset()
		
		populateValidParams(params)
		params["value"] = 'AAAA'
		controller.saveMealsDollarBuys()
		
		assert view ==  '/globalNumericSetting/createMealsADollarBuys'
		assert controller.flash.message != null
		assert GlobalNumericSetting.count() == 0
		
	}

    void testShowEmployeeGoal() {
        controller.showEmployeeGoal()

        assert flash.message != null
        assert response.redirectedUrl == '/globalNumericSetting/goalPerEmployee'

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null
		globalNumericSetting.save()
	
        params.id = globalNumericSetting.id

        def model = controller.showEmployeeGoal()

        assert model.globalNumericSettingInstance == globalNumericSetting
    }
	
	void testShowMealsDollarBuys() {
		controller.showMealsDollarBuys()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/mealsADollarBuys'

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null
		globalNumericSetting.save()
	
		params.id = globalNumericSetting.id

		def model = controller.showMealsDollarBuys()

		assert model.globalNumericSettingInstance == globalNumericSetting
	}
	
	void testShowMofbShiftValue() {
		controller.showMofbShiftValue()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/mofbShiftValue'

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null
		globalNumericSetting.save()
	
		params.id = globalNumericSetting.id

		def model = controller.showMofbShiftValue()

		assert model.globalNumericSettingInstance == globalNumericSetting
	}

    void testEditEmployeeGoal() {
        controller.editEmployeeGoal()

        assert flash.message != null
        assert response.redirectedUrl == '/globalNumericSetting/goalPerEmployee'

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null

        params.id = globalNumericSetting.id

        def model = controller.editEmployeeGoal()

        assert model.globalNumericSettingInstance == globalNumericSetting
    }
	
	void testEditMealsDollarBuys() {
		controller.editMealsDollarBuys()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/mealsADollarBuys'

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null

		params.id = globalNumericSetting.id

		def model = controller.editMealsDollarBuys()

		assert model.globalNumericSettingInstance == globalNumericSetting
	}
	
	void testEditMofbShiftValue() {
		controller.editMofbShiftValue()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/mofbShiftValue'

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null

		params.id = globalNumericSetting.id

		def model = controller.editMofbShiftValue()

		assert model.globalNumericSettingInstance == globalNumericSetting
	}


	
	void testUpdateEmployeeGoal() {
		controller.updateEmployeeGoal()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/goalPerEmployee'

		response.reset()

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null

		// test invalid parameters in update
		params.id = globalNumericSetting.id
		params.name = ''

		controller.updateEmployeeGoal()

		assert view == "/globalNumericSetting/editEmployeeGoal"
		assert model.globalNumericSettingInstance != null

		globalNumericSetting.clearErrors()

		populateValidParams(params)
		controller.updateEmployeeGoal()

		assert response.redirectedUrl == "/globalNumericSetting/showEmployeeGoal/$globalNumericSetting.id"
		assert flash.message != null

		//test outdated version number
		response.reset()
		globalNumericSetting.clearErrors()

		populateValidParams(params)
		params.id = globalNumericSetting.id
		params.version = -1
		controller.updateEmployeeGoal()

		assert view == "/globalNumericSetting/editEmployeeGoal"
		assert model.globalNumericSettingInstance != null
		assert model.globalNumericSettingInstance.errors.getFieldError('version')
		assert flash.message != null
	}
	
	
	
	void testUpdateMealsDollarBuys() {
		
		def result = false
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..3) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		controller.updateMealsDollarBuys()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/mealsADollarBuys'

		response.reset()

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null

		// test invalid parameters in update
		params.id = globalNumericSetting.id
		params.name = ''

		controller.updateMealsDollarBuys()

		assert view == "/globalNumericSetting/editMealsDollarBuys"
		assert model.globalNumericSettingInstance != null

		globalNumericSetting.clearErrors()

		populateValidParams(params)
		controller.updateMealsDollarBuys()

		assert response.redirectedUrl == "/globalNumericSetting/showMealsDollarBuys/$globalNumericSetting.id"
		assert flash.message != null

		//test outdated version number
		response.reset()
		globalNumericSetting.clearErrors()

		populateValidParams(params)
		params.id = globalNumericSetting.id
		params.version = -1
		controller.updateMealsDollarBuys()

		assert view == "/globalNumericSetting/editMealsDollarBuys"
		assert model.globalNumericSettingInstance != null
		assert model.globalNumericSettingInstance.errors.getFieldError('version')
		assert flash.message != null
	}
	
	void testUpdateMealsDollarBuysWithAnotherAlreadySavedWithThatDate () {
		
		def result = true
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.determineIfSettingDateHasBeenUsed(1..1) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null


		populateValidParams(params)
		params.id = globalNumericSetting.id
		controller.updateMealsDollarBuys()

		assert view == "/globalNumericSetting/editMealsDollarBuys"
		assert model.globalNumericSettingInstance != null
		assert flash.message != null
	}
	
	void testUpdateMofbShift() {
		def result = true
		def mockGlobalsService = mockFor(GlobalNumericSettingService)
		mockGlobalsService.demand.validateFoodBankShift(1..4) {String name, Date date -> return result }
		
		controller.globalNumericSettingService = mockGlobalsService.createMock()
		
		controller.updateMofbShift()

		assert flash.message != null
		assert response.redirectedUrl == '/globalNumericSetting/mofbShiftValue'

		response.reset()

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null

		// test invalid parameters in update
		params.id = globalNumericSetting.id
		params.name = ''

		controller.updateMofbShift()

		assert view == "/globalNumericSetting/editMofbShiftValue"
		assert model.globalNumericSettingInstance != null

		globalNumericSetting.clearErrors()

		populateValidParams(params)
		controller.updateMofbShift()

		assert response.redirectedUrl == "/globalNumericSetting/showMofbShiftValue/$globalNumericSetting.id"
		assert flash.message != null

		//test outdated version number
		response.reset()
		globalNumericSetting.clearErrors()

		populateValidParams(params)
		params.id = globalNumericSetting.id
		params.version = -1
		controller.updateMofbShift()

		assert view == "/globalNumericSetting/editMofbShiftValue"
		assert model.globalNumericSettingInstance != null
		assert model.globalNumericSettingInstance.errors.getFieldError('version')
		assert flash.message != null
	}

    void testDelete() {
        controller.delete()
        assert flash.message != null
      
        response.reset()

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null
        assert GlobalNumericSetting.count() == 1

        params.id = globalNumericSetting.id

        controller.delete()

        assert GlobalNumericSetting.count() == 0
        assert GlobalNumericSetting.get(globalNumericSetting.id) == null
      
    }
	
	void testDeleteEmployeeGoal() {
		controller.deleteEmployeeGoal()
		assert flash.message != null
	  
		response.reset()

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null
		assert GlobalNumericSetting.count() == 1

		params.id = globalNumericSetting.id

		controller.deleteEmployeeGoal()

		assert GlobalNumericSetting.count() == 0
		assert GlobalNumericSetting.get(globalNumericSetting.id) == null
	  
	}
	
	void testDeleteMealsDollarBuys() {
		controller.deleteMealsDollarBuys()
		assert flash.message != null
	  
		response.reset()

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null
		assert GlobalNumericSetting.count() == 1

		params.id = globalNumericSetting.id

		controller.deleteMealsDollarBuys()

		assert GlobalNumericSetting.count() == 0
		assert GlobalNumericSetting.get(globalNumericSetting.id) == null
	  
	}
	
	void testDeleteMofbShiftValue() {
		controller.deleteMofbShiftValue()
		assert flash.message != null
	  
		response.reset()

		populateValidParams(params)
		def globalNumericSetting = new GlobalNumericSetting(params)

		assert globalNumericSetting.save() != null
		assert GlobalNumericSetting.count() == 1

		params.id = globalNumericSetting.id

		controller.deleteMofbShiftValue()

		assert GlobalNumericSetting.count() == 0
		assert GlobalNumericSetting.get(globalNumericSetting.id) == null
	  
	}
}
