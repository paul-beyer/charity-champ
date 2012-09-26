package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class VolunteerShiftController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [volunteerShiftInstanceList: VolunteerShift.list(params), volunteerShiftInstanceTotal: VolunteerShift.count()]
    }

    def create() {
        [volunteerShiftInstance: new VolunteerShift(params)]
    }

    def save() {
		def mealFactor = 0
		if(params.mealFactor){
			mealFactor = params.mealFactor.toBigDecimal()
		}
        def volunteerShiftInstance = new VolunteerShift(params)
		volunteerShiftInstance.mealFactor = mealFactor
        if (!volunteerShiftInstance.save(flush: true)) {
            render(view: "create", model: [volunteerShiftInstance: volunteerShiftInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), volunteerShiftInstance.id])
        redirect(action: "show", id: volunteerShiftInstance.id)
    }

    def show(Long id) {
        def volunteerShiftInstance = VolunteerShift.get(id)
        if (!volunteerShiftInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), id])
            redirect(action: "list")
            return
        }

        [volunteerShiftInstance: volunteerShiftInstance]
    }

    def edit(Long id) {
        def volunteerShiftInstance = VolunteerShift.get(id)
        if (!volunteerShiftInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), id])
            redirect(action: "list")
            return
        }

        [volunteerShiftInstance: volunteerShiftInstance]
    }

    def update(Long id, Long version) {
        def volunteerShiftInstance = VolunteerShift.get(id)
        if (!volunteerShiftInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (volunteerShiftInstance.version > version) {
                volunteerShiftInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'volunteerShift.label', default: 'VolunteerShift')] as Object[],
                          "Another user has updated this VolunteerShift while you were editing")
                render(view: "edit", model: [volunteerShiftInstance: volunteerShiftInstance])
                return
            }
        }

        volunteerShiftInstance.properties = params
		def mealFactor = 0
		if(params.mealFactor){
			mealFactor = params.mealFactor.toBigDecimal()
		}
		volunteerShiftInstance.mealFactor = mealFactor
		
        if (!volunteerShiftInstance.save(flush: true)) {
            render(view: "edit", model: [volunteerShiftInstance: volunteerShiftInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), volunteerShiftInstance.id])
        redirect(action: "show", id: volunteerShiftInstance.id)
    }

    def delete(Long id) {
        def volunteerShiftInstance = VolunteerShift.get(id)
        if (!volunteerShiftInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), id])
            redirect(action: "list")
            return
        }

        try {
            volunteerShiftInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'volunteerShift.label', default: 'VolunteerShift'), id])
            redirect(action: "show", id: id)
        }
    }
}
