package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class JeansPaymentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [jeansPaymentInstanceList: JeansPayment.list(params), jeansPaymentInstanceTotal: JeansPayment.count()]
    }

    def create() {
        [jeansPaymentInstance: new JeansPayment(params)]
    }

    def save() {
		
		def amtPaid = 0
		if(params.amtPaid){
			amtPaid = params.amtPaid.toBigDecimal()
		}
        def jeansPaymentInstance = new JeansPayment(params)
		jeansPaymentInstance.amountCollected = amtPaid
        if (!jeansPaymentInstance.save(flush: true)) {
            render(view: "create", model: [jeansPaymentInstance: jeansPaymentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), jeansPaymentInstance.id])
        redirect(action: "show", id: jeansPaymentInstance.id)
    }

    def show(Long id) {
        def jeansPaymentInstance = JeansPayment.get(id)
        if (!jeansPaymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), id])
            redirect(action: "list")
            return
        }

        [jeansPaymentInstance: jeansPaymentInstance]
    }

    def edit(Long id) {
        def jeansPaymentInstance = JeansPayment.get(id)
        if (!jeansPaymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), id])
            redirect(action: "list")
            return
        }

        [jeansPaymentInstance: jeansPaymentInstance]
    }

    def update(Long id, Long version) {
        def jeansPaymentInstance = JeansPayment.get(id)
        if (!jeansPaymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (jeansPaymentInstance.version > version) {
                jeansPaymentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'jeansPayment.label', default: 'JeansPayment')] as Object[],
                          "Another user has updated this JeansPayment while you were editing")
                render(view: "edit", model: [jeansPaymentInstance: jeansPaymentInstance])
                return
            }
        }

        jeansPaymentInstance.properties = params
		def amtPaid = 0
		if(params.amtPaid){
			amtPaid = params.amtPaid.toBigDecimal()
		}
		jeansPaymentInstance.amountCollected = amtPaid
        if (!jeansPaymentInstance.save(flush: true)) {
            render(view: "edit", model: [jeansPaymentInstance: jeansPaymentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), jeansPaymentInstance.id])
        redirect(action: "show", id: jeansPaymentInstance.id)
    }

    def delete(Long id) {
        def jeansPaymentInstance = JeansPayment.get(id)
        if (!jeansPaymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), id])
            redirect(action: "list")
            return
        }

        try {
            jeansPaymentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'jeansPayment.label', default: 'JeansPayment'), id])
            redirect(action: "show", id: id)
        }
    }
}
