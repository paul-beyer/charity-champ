package com.charitychamp



import org.junit.*
import grails.test.mixin.*

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
		controller.saveMealsDollarBuys()

		assert model.globalNumericSettingInstance != null
		assert view == '/globalNumericSetting/createMealsADollarBuys'

		response.reset()

		populateValidParams(params)
		controller.saveMealsDollarBuys()

		assert response.redirectedUrl == "/globalNumericSetting/mealsADollarBuys"
		assert controller.flash.message != null
		assert GlobalNumericSetting.count() == 1
	}
	
	void testSaveMealsDollarBuysWithBadNumericInput(){
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
	
	void testUpdateMofbShift() {
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
