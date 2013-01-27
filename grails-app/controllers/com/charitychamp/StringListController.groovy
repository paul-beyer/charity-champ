package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class StringListController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [stringListInstanceList: StringList.list(params), stringListInstanceTotal: StringList.count()]
    }

    def create() {
		def activityType = new StringList(listName : 'Activity Type')
        [stringListInstance: activityType]
    }

    def save() {
        def stringListInstance = new StringList(params)
        if (!stringListInstance.save(flush: true)) {
            render(view: "create", model: [stringListInstance: stringListInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stringList.label', default: 'StringList'), stringListInstance.id])
		redirect(action: "list")
    }

    def show(Long id) {
        def stringListInstance = StringList.get(id)
        if (!stringListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stringList.label', default: 'StringList'), id])
            redirect(action: "list")
            return
        }

        [stringListInstance: stringListInstance]
    }

    def edit(Long id) {
        def stringListInstance = StringList.get(id)
        if (!stringListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stringList.label', default: 'StringList'), id])
            redirect(action: "list")
            return
        }

        [stringListInstance: stringListInstance]
    }

    def update(Long id, Long version) {
        def stringListInstance = StringList.get(id)
        if (!stringListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stringList.label', default: 'StringList'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stringListInstance.version > version) {
                stringListInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'stringList.label', default: 'StringList')] as Object[],
                          "Another user has updated this StringList while you were editing")
                render(view: "edit", model: [stringListInstance: stringListInstance])
                return
            }
        }

        stringListInstance.properties = params

        if (!stringListInstance.save(flush: true)) {
            render(view: "edit", model: [stringListInstance: stringListInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stringList.label', default: 'StringList'), stringListInstance.id])
       redirect(action: "list")
    }

    def delete(Long id) {
        def stringListInstance = StringList.get(id)
        if (!stringListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stringList.label', default: 'StringList'), id])
            redirect(action: "list")
            return
        }

        try {
            stringListInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'stringList.label', default: 'StringList'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'stringList.label', default: 'StringList'), id])
            redirect(action: "show", id: id)
        }
    }
}
