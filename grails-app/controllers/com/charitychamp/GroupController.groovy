package com.charitychamp

import java.text.NumberFormat

import org.springframework.dao.DataIntegrityViolationException

class GroupController {
	
	def donationService
	def dateHandlerService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [groupInstanceList: Group.list(params), groupInstanceTotal: Group.count()]
    }

    def create() {
        [groupInstance: new Group(params)]
    }

    def save() {
        def groupInstance = new Group(params)
        if (!groupInstance.save(flush: true)) {
            render(view: "create", model: [groupInstance: groupInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'group.label', default: 'Group'), groupInstance.id])
        redirect(action: "show", id: groupInstance.id)
    }

    def show(Long id) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        [groupInstance: groupInstance, secondGroupInstance : groupInstance]
    }

	def overview(Long id) {
		
		log.debug("Entering overview")

		def groupInstance = Group.get(id)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
			redirect(action: "list")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
		
		commonGroupActivityReturnValues(groupInstance, currentCampaign)
	
		
	}
	
	private Map commonGroupActivityReturnValues(Group groupInstance, Campaign currentCampaign){
		
		[groupInstance: groupInstance, departmentName : groupInstance.department?.name, departmentId : groupInstance.department?.id
			, officeId : groupInstance.department?.office?.id, officeName : groupInstance.department?.office?.name
			, businessId : groupInstance.department?.office?.business?.id, businessName : groupInstance.department?.office?.business?.name
			, companyId :  groupInstance.department?.office?.business?.company?.id,  companyName :  groupInstance.department?.office?.business?.company?.name
			, currentCampaign : currentCampaign]
		
	}

	
    def edit(Long id) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        [groupInstance: groupInstance]
    }

    def update(Long id, Long version) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (groupInstance.version > version) {
                groupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'group.label', default: 'Group')] as Object[],
                          "Another user has updated this Group while you were editing")
                render(view: "edit", model: [groupInstance: groupInstance])
                return
            }
        }

        groupInstance.properties = params

        if (!groupInstance.save(flush: true)) {
            render(view: "edit", model: [groupInstance: groupInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'group.label', default: 'Group'), groupInstance.id])
        redirect(action: "show", id: groupInstance.id)
    }

    def delete(Long id) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        try {
            groupInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def activities() {
	
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(action: "overview", id: groupId)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
		
		def activityList = new ArrayList()
		if(currentCampaign){
			activityList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.activity)
		}
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('activityInstanceList', activityList)
		returnModel.put('activityInstanceTotal', activityList.size())
		
		return returnModel     
    }
	
	def addActivity() {

		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(action: "overview", id: groupId)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
		
		commonGroupActivityReturnValues(groupInstance, currentCampaign)
				
		
	}
	

	def saveActivity(){
		
		log.debug("Entering activity save")
		
		def activityInstance = new Activity(params)
		activityInstance.donationDate = params.depositDate
				
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(action: "overview", id: groupId)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('activityInstance', activityInstance)
			
		if(!currentCampaign){
			flash.message = message(code: 'campaign.not.found', args: [todaysDate])
		
			render(view: "addActivity", model: returnModel)
			return
		}
				
	
		def depositAmount = 0
	
		if(params.amountCollected){
			try{
				def numberAmount = NumberFormat.getNumberInstance().parse(params.amountCollected)
				depositAmount = numberAmount.toBigDecimal()
			}catch(Exception ex){
				flash.message = message(code: 'activity.amount.collected.parse.exception')
				log.error("Exception occurred while formating amount collected in saveActivity", ex)
				render(view: "addActivity", model: returnModel)
				return
			}
		
		}
		
		boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.depositDate)
		if(!donationDateIsGood){
			flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.depositDate])
			render(view: "addActivity", model: returnModel)
			return
			
		}
		

		if (!activityInstance.save(flush: true)) {	
			
			render(view: "addActivity", model: returnModel)
			return
		}
		
		def donationSource = new DonationSource(donation: activityInstance, orgUnit : groupInstance).save(flush:true)
		currentCampaign.addToDonationSources(donationSource).save(flush:true)
		flash.message = message(code: 'default.created.message', args: [message(code: 'activity.label', default: 'Activity'), activityInstance.id])
		redirect(action: "activities", id: groupInstance.id)
		
	}
	
	def editActivity(Long id) {
			
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(action: "overview", id: groupId)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
		
		def activityInstance = Activity.get(id)
		if (!activityInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), id])
			redirect(action: "activities", id : groupInstance.id)
			return
		}
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('activityInstance', activityInstance)

		return returnModel
	}
	
	def updateActivity(Long id, Long activityId, Long activityVersion){
	
		def groupInstance = Group.get(id)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def activityInstance = Activity.get(activityId)
		if (!activityInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), activityId])
			redirect(action: "activities", id : groupInstance.id)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId.toLong())
			
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('activityInstance', activityInstance)
		
		if (activityVersion) {
			if (activityInstance.version > activityVersion) {
			
				activityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'activity.label', default: 'Activity')] as Object[],
						  "Another user has updated this Activity while you were editing")
				render(view: "editActivity", model: returnModel)
				return
			}
		}
		
		activityInstance.properties = params
		def depositAmount = 0
		
		if(params.amountCollected){
			try{
				def numberAmount = NumberFormat.getNumberInstance().parse(params.amountCollected)
				depositAmount = numberAmount.toBigDecimal()
			}catch(Exception ex){
				flash.message = message(code: 'activity.amount.collected.parse.exception')
				log.error("Exception occurred while formating amount collected in updateActivity", ex)
				render(view: "editActivity", model: returnModel)
				return
			}
			activityInstance.amountCollected = depositAmount
		}
		
		
		
		def selectedCampaign = Campaign.get(params.campaignId.toLong())
				
		boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(selectedCampaign, params.depositDate)
		if(!donationDateIsGood){
			flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.depositDate])
			render(view: "editActivity", model: returnModel)
			return
		}

		groupInstance.properties = params

		if (!groupInstance.save(flush: true)) {
			render(view: "edit", model: [groupInstance: groupInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'group.label', default: 'Group'), groupInstance.id])
		redirect(action: "show", id: groupInstance.id)
		
	}
	
	
}
