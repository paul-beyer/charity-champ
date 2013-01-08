package com.charitychamp

import org.joda.time.DateMidnight
import org.joda.time.Interval
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
		def reversed = areCampaignDatesReversed(params.startDate, params.endDate)
	
		if(reversed){
			flash.message = message(code: 'campaign.dates.reversed')
			render(view: "create", model: [campaignInstance: campaignInstance])
			return
		}
		
		def campaignsOverlap = doCampaignsOverlap(null, params.startDate, params.endDate, false)
		
		if(campaignsOverlap){
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
		
		def updateAction = true
		
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
		
		def reversed = areCampaignDatesReversed(params.startDate, params.endDate)
			
		if(reversed){
			flash.message = message(code: 'campaign.dates.reversed')
			render(view: "edit", model: [campaignInstance: campaignInstance])
			return
		}
			
		def campaignsOverlap = doCampaignsOverlap(id, params.startDate, params.endDate, updateAction)
			
		if(campaignsOverlap){
			flash.message = message(code: 'campaign.dates.overlap')
			render(view: "edit", model: [campaignInstance: campaignInstance])
			return
			
		}
		
		def donationsOrphaned = doDonationsDropOutOfCampaign(id, params.startDate, params.endDate)
		
		if(donationsOrphaned){
			flash.message = message(code: 'campaign.donations.are.orphaned')
			render(view: "edit", model: [campaignInstance: campaignInstance])
			return
			
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
		
		if(campaignInstance.donationSources?.size() > 0){
			flash.message = message(code: 'campaign.not.deleted.message', args: [message(code: 'campaign.label', default: 'Campaign'), id])
            redirect(action: "show", id: id)
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
	
	private boolean areCampaignDatesReversed(Date startDate, Date endDate){
		
		def reversed = false
		
		def newCampaignStartJoda = new LocalDate(startDate)
		def newCampaignEndJoda = new LocalDate(endDate)
		def newCampaignEndJodaMinusOneDay = newCampaignEndJoda.minusDays(1)
	
		if(newCampaignStartJoda.isAfter(newCampaignEndJodaMinusOneDay)){
			reversed = true
		}
		
		return reversed
	}
	
	private boolean doCampaignsOverlap(Long id, Date startDate, Date endDate,  boolean isUpdate){
		
		def newCampaignStartJoda = new LocalDate(startDate)
		def newCampaignEndJoda = new LocalDate(endDate)
		
		def existingCampaigns
		
		//if Update, we need to remove the campaign we intend to update from the evaluation list
		if(isUpdate){
			def c = Campaign.createCriteria()
			existingCampaigns = c.list {
			    ne("id", id)
			}
		}else{ 		
			existingCampaigns = Campaign.findAll()
		}
		
		def campaignsOverlap = false
		existingCampaigns.each {
			def existingStart = new LocalDate(it.startDate)
			def existingEnd = new LocalDate(it.endDate)
			
			def newCampaignStartPlusOneDay = newCampaignStartJoda.plusDays(1)
			def newCampaignStartMinusOneDay = newCampaignStartJoda.minusDays(1)
			
			def newCampaignEndPlusOneDate = newCampaignEndJoda.plusDays(1)
			def newCampaignEndMinusOneDate = newCampaignEndJoda.minusDays(1)
			
			if(newCampaignStartPlusOneDay.isAfter(existingStart) && newCampaignStartMinusOneDay.isBefore(existingEnd)){
				campaignsOverlap = true
				return campaignsOverlap
			}
			
			if(newCampaignEndPlusOneDate.isAfter(existingStart) && newCampaignEndMinusOneDate.isBefore(existingEnd)){
				campaignsOverlap = true
				return campaignsOverlap
			}
		}
	
		return campaignsOverlap
	}
	
	private doDonationsDropOutOfCampaign(Long id, Date startDate, Date endDate){
		
		def donationsBeingOrphaned = false
		
		def newCampaignStartJoda = new LocalDate(startDate)
		def newCampaignEndJoda = new LocalDate(endDate)
		
		def foundCampaign = Campaign.get(id)
		if(foundCampaign){
			def donations = foundCampaign.donationSources
			//using for loop so I can break out
			for(donation in donations){
				def donationDate = new LocalDate(donation.donation?.donationDate)
				if(donationDate.isBefore(newCampaignStartJoda) || donationDate.isAfter(newCampaignEndJoda)){
					donationsBeingOrphaned = true
					break
				}
				
			}
			
		}
		
		return donationsBeingOrphaned
	}
}
