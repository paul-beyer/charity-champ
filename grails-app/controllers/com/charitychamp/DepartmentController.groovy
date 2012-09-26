package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class DepartmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [departmentInstanceList: Department.list(params), departmentInstanceTotal: Department.count()]
    }

    def create() {
        [departmentInstance: new Department(params)]
    }

    def save() {
        def departmentInstance = new Department(params)
        if (!departmentInstance.save(flush: true)) {
            render(view: "create", model: [departmentInstance: departmentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'department.label', default: 'Department'), departmentInstance.id])
        redirect(action: "show", id: departmentInstance.id)
    }

    def show(Long id) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        [departmentInstance: departmentInstance]
    }

    def edit(Long id) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        [departmentInstance: departmentInstance]
    }

    def update(Long id, Long version) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (departmentInstance.version > version) {
                departmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'department.label', default: 'Department')] as Object[],
                          "Another user has updated this Department while you were editing")
                render(view: "edit", model: [departmentInstance: departmentInstance])
                return
            }
        }

        departmentInstance.properties = params

        if (!departmentInstance.save(flush: true)) {
            render(view: "edit", model: [departmentInstance: departmentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'department.label', default: 'Department'), departmentInstance.id])
        redirect(action: "show", id: departmentInstance.id)
    }

    def delete(Long id) {
        def departmentInstance = Department.get(id)
        if (!departmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
            return
        }

        try {
            departmentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'department.label', default: 'Department'), id])
            redirect(action: "show", id: id)
        }
    }
}
