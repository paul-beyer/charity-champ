package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class DonationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [donationInstanceList: Donation.list(params), donationInstanceTotal: Donation.count()]
    }

    def create() {
        [donationInstance: new Donation(params)]
    }

    def save() {
        def donationInstance = new Donation(params)
        if (!donationInstance.save(flush: true)) {
            render(view: "create", model: [donationInstance: donationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'donation.label', default: 'Donation'), donationInstance.id])
        redirect(action: "show", id: donationInstance.id)
    }

    def show(Long id) {
        def donationInstance = Donation.get(id)
        if (!donationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'donation.label', default: 'Donation'), id])
            redirect(action: "list")
            return
        }

        [donationInstance: donationInstance]
    }

    def edit(Long id) {
        def donationInstance = Donation.get(id)
        if (!donationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'donation.label', default: 'Donation'), id])
            redirect(action: "list")
            return
        }

        [donationInstance: donationInstance]
    }

    def update(Long id, Long version) {
        def donationInstance = Donation.get(id)
        if (!donationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'donation.label', default: 'Donation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (donationInstance.version > version) {
                donationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'donation.label', default: 'Donation')] as Object[],
                          "Another user has updated this Donation while you were editing")
                render(view: "edit", model: [donationInstance: donationInstance])
                return
            }
        }

        donationInstance.properties = params

        if (!donationInstance.save(flush: true)) {
            render(view: "edit", model: [donationInstance: donationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'donation.label', default: 'Donation'), donationInstance.id])
        redirect(action: "show", id: donationInstance.id)
    }

    def delete(Long id) {
        def donationInstance = Donation.get(id)
        if (!donationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'donation.label', default: 'Donation'), id])
            redirect(action: "list")
            return
        }

        try {
            donationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'donation.label', default: 'Donation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'donation.label', default: 'Donation'), id])
            redirect(action: "show", id: id)
        }
    }
}
