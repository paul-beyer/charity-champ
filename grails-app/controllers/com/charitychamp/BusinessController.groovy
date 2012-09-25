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

class BusinessController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [businessInstanceList: Business.list(params), businessInstanceTotal: Business.count()]
    }

    def create() {
        [businessInstance: new Business(params)]
    }

    def save() {
        def businessInstance = new Business(params)
        if (!businessInstance.save(flush: true)) {
            render(view: "create", model: [businessInstance: businessInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'business.label', default: 'Business'), businessInstance.id])
        redirect(action: "show", id: businessInstance.id)
    }

    def show(Long id) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        [businessInstance: businessInstance]
    }

    def edit(Long id) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        [businessInstance: businessInstance]
    }

    def update(Long id, Long version) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (businessInstance.version > version) {
                businessInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'business.label', default: 'Business')] as Object[],
                          "Another user has updated this Business while you were editing")
                render(view: "edit", model: [businessInstance: businessInstance])
                return
            }
        }

        businessInstance.properties = params

        if (!businessInstance.save(flush: true)) {
            render(view: "edit", model: [businessInstance: businessInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'business.label', default: 'Business'), businessInstance.id])
        redirect(action: "show", id: businessInstance.id)
    }

    def delete(Long id) {
        def businessInstance = Business.get(id)
        if (!businessInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
            return
        }

        try {
            businessInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'business.label', default: 'Business'), id])
            redirect(action: "show", id: id)
        }
    }
}
