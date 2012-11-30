/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.charitychamp

import org.junit.*
import grails.test.mixin.*

@TestFor(CompanyController)
@Mock(Company)
class CompanyControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["leader"] = new Person(userId:'biffle', firstName: "Greg", lastName: "Biffle")
		params["name"] = 'PepsiCo'
    }

    void testIndex() {
        controller.index()
        assert "/company/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.companyInstanceList.size() == 0
        assert model.companyInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.companyInstance != null
    }

    void testSave() {
        controller.save()

        assert model.companyInstance != null
        assert view == '/company/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/company/show/1'
        assert controller.flash.message != null
        assert Company.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/company/list'

        populateValidParams(params)
        def company = new Company(params)

        assert company.save() != null

        params.id = company.id

        def model = controller.show()

        assert model.companyInstance == company
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/company/list'

        populateValidParams(params)
        def company = new Company(params)

        assert company.save() != null

        params.id = company.id

        def model = controller.edit()

        assert model.companyInstance == company
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/company/list'

        response.reset()

        populateValidParams(params)
        def company = new Company(params)

        assert company.save() != null

        // test invalid parameters in update
        params.id = company.id
        params.name = ''
		
        controller.update()

        assert view == "/company/edit"
        assert model.companyInstance != null

        company.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/company/show/$company.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        company.clearErrors()

        populateValidParams(params)
        params.id = company.id
        params.version = -1
        controller.update()

        assert view == "/company/edit"
        assert model.companyInstance != null
        assert model.companyInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/company/list'

        response.reset()

        populateValidParams(params)
        def company = new Company(params)

        assert company.save() != null
        assert Company.count() == 1

        params.id = company.id

        controller.delete()

        assert Company.count() == 0
        assert Company.get(company.id) == null
        assert response.redirectedUrl == '/company/list'
    }
}
