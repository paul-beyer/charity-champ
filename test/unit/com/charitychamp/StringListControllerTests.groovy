package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(StringListController)
@Mock(StringList)
class StringListControllerTests {

    def populateValidParams(params) {
        assert params != null
       
        params["listName"] = 'Activity'
		params["value"] = 'Cookoff'
    }

    void testIndex() {
        controller.index()
        assert "/stringList/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.stringListInstanceList.size() == 0
        assert model.stringListInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.stringListInstance != null
    }

    void testSave() {
        controller.save()

        assert model.stringListInstance != null
        assert view == '/stringList/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/stringList/list'
        assert controller.flash.message != null
        assert StringList.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/stringList/list'

        populateValidParams(params)
        def stringList = new StringList(params)

        assert stringList.save() != null

        params.id = stringList.id

        def model = controller.show()

        assert model.stringListInstance == stringList
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/stringList/list'

        populateValidParams(params)
        def stringList = new StringList(params)

        assert stringList.save() != null

        params.id = stringList.id

        def model = controller.edit()

        assert model.stringListInstance == stringList
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/stringList/list'

        response.reset()

        populateValidParams(params)
        def stringList = new StringList(params)

        assert stringList.save() != null

        // test invalid parameters in update
        params.id = stringList.id
        params.listName = ''

        controller.update()

        assert view == "/stringList/edit"
        assert model.stringListInstance != null

        stringList.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/stringList/list"
        assert flash.message != null

        //test outdated version number
        response.reset()
        stringList.clearErrors()

        populateValidParams(params)
        params.id = stringList.id
        params.version = -1
        controller.update()

        assert view == "/stringList/edit"
        assert model.stringListInstance != null
        assert model.stringListInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/stringList/list'

        response.reset()

        populateValidParams(params)
        def stringList = new StringList(params)

        assert stringList.save() != null
        assert StringList.count() == 1

        params.id = stringList.id

        controller.delete()

        assert StringList.count() == 0
        assert StringList.get(stringList.id) == null
        assert response.redirectedUrl == '/stringList/list'
    }
}
