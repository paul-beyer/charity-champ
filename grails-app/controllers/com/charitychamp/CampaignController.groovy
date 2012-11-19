package com.charitychamp

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.springframework.dao.DataIntegrityViolationException

class CampaignController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [campaignInstanceList: Campaign.list(params), campaignInstanceTotal: Campaign.count()]
    }

    def create() {
        [campaignInstance: new Campaign(params)]
    }

    def save() {
        def campaignInstance = new Campaign(params)
		def newCampaignStartJoda = new LocalDate(campaignInstance?.startDate)
		def newCampaignEndJoda = new LocalDate(campaignInstance?.endDate)
		def newCampaignEndJodaMinusOneDay = newCampaignEndJoda.minusDays(1)
	
		if(newCampaignStartJoda.isAfter(newCampaignEndJodaMinusOneDay)){
			flash.message = message(code: 'campaign.dates.reversed')
			render(view: "create", model: [campaignInstance: campaignInstance])
			return
		}
		def existingCampaigns = Campaign.findAll()
		def campaignsOverlap = false
		existingCampaigns.each {
			def jodaEnd = new LocalDate(it.endDate)
			def newCampaignStartJodaMinusOneDay = newCampaignStartJoda.minusDays(1)
			if(newCampaignStartJodaMinusOneDay.isBefore(jodaEnd)){
				campaignsOverlap = true
				log.debug("Campaigns overlap")
			}
		}
		if(campaignsOverlap == true){
			flash.message = message(code: 'campaign.dates.overlap')
			render(view: "create", model: [campaignInstance: campaignInstance])
			return
			
		}
        if (!campaignInstance.save(flush: true)) {
            render(view: "create", model: [campaignInstance: campaignInstance])
            return
        }
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'campaign.label', default: 'Campaign'), campaignInstance.id])
        redirect(action: "show", id: campaignInstance.id)
    }

    def show(Long id) {
        def campaignInstance = Campaign.get(id)
        if (!campaignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "list")
            return
        }

        [campaignInstance: campaignInstance]
    }

    def edit(Long id) {
        def campaignInstance = Campaign.get(id)
        if (!campaignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "list")
            return
        }

        [campaignInstance: campaignInstance]
    }

    def update(Long id, Long version) {
        def campaignInstance = Campaign.get(id)
        if (!campaignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (campaignInstance.version > version) {
                campaignInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'campaign.label', default: 'Campaign')] as Object[],
                          "Another user has updated this Campaign while you were editing")
                render(view: "edit", model: [campaignInstance: campaignInstance])
                return
            }
        }

        campaignInstance.properties = params

        if (!campaignInstance.save(flush: true)) {
            render(view: "edit", model: [campaignInstance: campaignInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'campaign.label', default: 'Campaign'), campaignInstance.id])
        redirect(action: "show", id: campaignInstance.id)
    }

    def delete(Long id) {
        def campaignInstance = Campaign.get(id)
        if (!campaignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "list")
            return
        }

        try {
            campaignInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "show", id: id)
        }
    }
}
