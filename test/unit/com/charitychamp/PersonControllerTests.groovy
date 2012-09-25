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

@TestFor(PersonController)
@Mock(Person)
class PersonControllerTests {

    def populateValidParams(params) {
        assert params != null
       
        params["userId"] = 'cyclops'
		params["firstName"] = 'John'
		params["lastName"] = 'Johnson'
    }

    void testIndex() {
        controller.index()
        assert "/person/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.personInstanceList.size() == 0
        assert model.personInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.personInstance != null
    }

    void testSave() {
        controller.save()

        assert model.personInstance != null
        assert view == '/person/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/person/show/1'
        assert controller.flash.message != null
        assert Person.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/person/list'

        populateValidParams(params)
        def person = new Person(params)

        assert person.save() != null

        params.id = person.id

        def model = controller.show()

        assert model.personInstance == person
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/person/list'

        populateValidParams(params)
        def person = new Person(params)

        assert person.save() != null

        params.id = person.id

        def model = controller.edit()

        assert model.personInstance == person
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/person/list'

        response.reset()

        populateValidParams(params)
        def person = new Person(params)

        assert person.save() != null

        // test invalid parameters in update
        params.id = person.id
        params.userId = ''

        controller.update()

        assert view == "/person/edit"
        assert model.personInstance != null

        person.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/person/show/$person.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        person.clearErrors()

        populateValidParams(params)
        params.id = person.id
        params.version = -1
        controller.update()

        assert view == "/person/edit"
        assert model.personInstance != null
        assert model.personInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/person/list'

        response.reset()

        populateValidParams(params)
        def person = new Person(params)

        assert person.save() != null
        assert Person.count() == 1

        params.id = person.id

        controller.delete()

        assert Person.count() == 0
        assert Person.get(person.id) == null
        assert response.redirectedUrl == '/person/list'
    }
}
