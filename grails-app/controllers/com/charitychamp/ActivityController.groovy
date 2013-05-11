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

import org.springframework.dao.DataIntegrityViolationException

class ActivityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [activityInstanceList: Activity.list(params), activityInstanceTotal: Activity.count()]
    }

    def create() {
        [activityInstance: new Activity(params)]
    }

    def save() {
	
		def depositAmount = 0
	
		if(params.amountCollected){
			depositAmount = params.amountCollected.toBigDecimal()
		}
	
		def activityInstance = new Activity(params)
		activityInstance.amountCollected = depositAmount
		
        if (!activityInstance.save(flush: true)) {
		    render(view: "create", model: [activityInstance: activityInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'activity.label', default: 'Activity'), activityInstance.id])
        redirect(action: "show", id: activityInstance.id)
    }

    def show(Long id) {
        def activityInstance = Activity.get(id)
        if (!activityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), id])
            redirect(action: "list")
            return
        }

        [activityInstance: activityInstance]
    }

    def edit(Long id) {
        def activityInstance = Activity.get(id)
        if (!activityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), id])
            redirect(action: "list")
            return
        }

        [activityInstance: activityInstance]
    }

    def update(Long id, Long version) {
        def activityInstance = Activity.get(id)
        if (!activityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (activityInstance.version > version) {
                activityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'activity.label', default: 'Activity')] as Object[],
                          "Another user has updated this Activity while you were editing")
                render(view: "edit", model: [activityInstance: activityInstance])
                return
            }
        }

		activityInstance.properties = params
		def depositAmount = 0
	
		if(params.amountCollected){
			depositAmount = params.amountCollected.toBigDecimal()
		}
	
		activityInstance.amountCollected = depositAmount
		
        if (!activityInstance.save(flush: true)) {
            render(view: "edit", model: [activityInstance: activityInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'activity.label', default: 'Activity'), activityInstance.id])
        redirect(action: "show", id: activityInstance.id)
    }

    def delete(Long id) {
        def activityInstance = Activity.get(id)
        if (!activityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), id])
            redirect(action: "list")
            return
        }

        try {
            activityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'activity.label', default: 'Activity'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'activity.label', default: 'Activity'), id])
            redirect(action: "show", id: id)
        }
    }
}
