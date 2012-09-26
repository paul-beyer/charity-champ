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

    void testSave() {
        controller.save()

        assert model.globalNumericSettingInstance != null
        assert view == '/globalNumericSetting/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/globalNumericSetting/show/1'
        assert controller.flash.message != null
        assert GlobalNumericSetting.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/globalNumericSetting/list'

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null

        params.id = globalNumericSetting.id

        def model = controller.show()

        assert model.globalNumericSettingInstance == globalNumericSetting
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/globalNumericSetting/list'

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null

        params.id = globalNumericSetting.id

        def model = controller.edit()

        assert model.globalNumericSettingInstance == globalNumericSetting
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/globalNumericSetting/list'

        response.reset()

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null

        // test invalid parameters in update
        params.id = globalNumericSetting.id
        params.name = ''

        controller.update()

        assert view == "/globalNumericSetting/edit"
        assert model.globalNumericSettingInstance != null

        globalNumericSetting.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/globalNumericSetting/show/$globalNumericSetting.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        globalNumericSetting.clearErrors()

        populateValidParams(params)
        params.id = globalNumericSetting.id
        params.version = -1
        controller.update()

        assert view == "/globalNumericSetting/edit"
        assert model.globalNumericSettingInstance != null
        assert model.globalNumericSettingInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/globalNumericSetting/list'

        response.reset()

        populateValidParams(params)
        def globalNumericSetting = new GlobalNumericSetting(params)

        assert globalNumericSetting.save() != null
        assert GlobalNumericSetting.count() == 1

        params.id = globalNumericSetting.id

        controller.delete()

        assert GlobalNumericSetting.count() == 0
        assert GlobalNumericSetting.get(globalNumericSetting.id) == null
        assert response.redirectedUrl == '/globalNumericSetting/list'
    }
}
