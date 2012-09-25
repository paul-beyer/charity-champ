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

@TestFor(ActivityController)
@Mock(Activity)
class ActivityControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["name"] = 'Bake Sale'
		params["amountCollected"] = '100.50'
		params["depositDate"] = new Date().parse("yyyy-MM-dd", '2011-10-05')
    }

    void testIndex() {
        controller.index()
        assert "/activity/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.activityInstanceList.size() == 0
        assert model.activityInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.activityInstance != null
    }

    void testSave() {
        controller.save()

        assert model.activityInstance != null
        assert view == '/activity/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/activity/show/1'
        assert controller.flash.message != null
        assert Activity.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/activity/list'

        populateValidParams(params)
        def activity = new Activity(params)
		
		def depositAmount = params.amountCollected.toBigDecimal()
	
		activity.amountCollected = depositAmount
		
        assert activity.save() != null

        params.id = activity.id

        def model = controller.show()

        assert model.activityInstance == activity
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/activity/list'

        populateValidParams(params)
        def activity = new Activity(params)

		def depositAmount = params.amountCollected.toBigDecimal()
	
		activity.amountCollected = depositAmount
				
        assert activity.save() != null

        params.id = activity.id

        def model = controller.edit()

        assert model.activityInstance == activity
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/activity/list'

        response.reset()

        populateValidParams(params)
        def activity = new Activity(params)
		
		def depositAmount = params.amountCollected.toBigDecimal()
	
		activity.amountCollected = depositAmount
	
        assert activity.save() != null

        // test invalid parameters in update
        params.id = activity.id
        //TODO: add invalid values to params object
		params["name"] = ''

        controller.update()

        assert view == "/activity/edit"
        assert model.activityInstance != null

        activity.clearErrors()

        populateValidParams(params)
				
        controller.update()

        assert response.redirectedUrl == "/activity/show/$activity.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        activity.clearErrors()

        populateValidParams(params)
        params.id = activity.id
        params.version = -1
        controller.update()

        assert view == "/activity/edit"
        assert model.activityInstance != null
        assert model.activityInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/activity/list'

        response.reset()

        populateValidParams(params)
        def activity = new Activity(params)
		
		def depositAmount = params.amountCollected.toBigDecimal()
	
		activity.amountCollected = depositAmount
		
        assert activity.save() != null
        assert Activity.count() == 1

        params.id = activity.id

        controller.delete()

        assert Activity.count() == 0
        assert Activity.get(activity.id) == null
        assert response.redirectedUrl == '/activity/list'
    }
}
