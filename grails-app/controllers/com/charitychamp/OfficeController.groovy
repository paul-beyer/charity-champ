package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class OfficeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [officeInstanceList: Office.list(params), officeInstanceTotal: Office.count()]
    }

    def create() {
        [officeInstance: new Office(params)]
    }

    def save() {
        def officeInstance = new Office(params)
        if (!officeInstance.save(flush: true)) {
            render(view: "create", model: [officeInstance: officeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'office.label', default: 'Office'), officeInstance.id])
        redirect(action: "show", id: officeInstance.id)
    }

    def show(Long id) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        [officeInstance: officeInstance]
    }

    def edit(Long id) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        [officeInstance: officeInstance]
    }

    def update(Long id, Long version) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (officeInstance.version > version) {
                officeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'office.label', default: 'Office')] as Object[],
                          "Another user has updated this Office while you were editing")
                render(view: "edit", model: [officeInstance: officeInstance])
                return
            }
        }

        officeInstance.properties = params

        if (!officeInstance.save(flush: true)) {
            render(view: "edit", model: [officeInstance: officeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'office.label', default: 'Office'), officeInstance.id])
        redirect(action: "show", id: officeInstance.id)
    }

    def delete(Long id) {
        def officeInstance = Office.get(id)
        if (!officeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
            return
        }

        try {
            officeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'office.label', default: 'Office'), id])
            redirect(action: "show", id: id)
        }
    }
}
