package com.charitychamp



import org.junit.*
import grails.test.mixin.*

@TestFor(DepartmentController)
@Mock(Department)
class DepartmentControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["name"] = 'CIM/CIF'
		params["departmentHead"] = new Person(userId:'gambit', firstName: "Remy", lastName: "LeBeau")
		params["charityLieutenant"] = new Person(userId:'biffle', firstName: "Greg", lastName: "Biffle")
		params["office"] = new Office(name:'some office name')
    }

    void testIndex() {
        controller.index()
        assert "/department/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.departmentInstanceList.size() == 0
        assert model.departmentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.departmentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.departmentInstance != null
        assert view == '/department/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/department/show/1'
        assert controller.flash.message != null
        assert Department.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/department/list'

        populateValidParams(params)
        def department = new Department(params)

        assert department.save() != null

        params.id = department.id

        def model = controller.show()

        assert model.departmentInstance == department
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/department/list'

        populateValidParams(params)
        def department = new Department(params)

        assert department.save() != null

        params.id = department.id

        def model = controller.edit()

        assert model.departmentInstance == department
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/department/list'

        response.reset()

        populateValidParams(params)
        def department = new Department(params)

        assert department.save() != null

        // test invalid parameters in update
        params.id = department.id
        params.name = ''

        controller.update()

        assert view == "/department/edit"
        assert model.departmentInstance != null

        department.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/department/show/$department.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        department.clearErrors()

        populateValidParams(params)
        params.id = department.id
        params.version = -1
        controller.update()

        assert view == "/department/edit"
        assert model.departmentInstance != null
        assert model.departmentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/department/list'

        response.reset()

        populateValidParams(params)
        def department = new Department(params)

        assert department.save() != null
        assert Department.count() == 1

        params.id = department.id

        controller.delete()

        assert Department.count() == 0
        assert Department.get(department.id) == null
        assert response.redirectedUrl == '/department/list'
    }
}
