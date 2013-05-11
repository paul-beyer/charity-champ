package com.charitychamp

import java.text.NumberFormat

import org.joda.time.LocalDate
import org.springframework.dao.DataIntegrityViolationException

class GroupController {
	
	def activityService
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
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
				
		def returnObject = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		
		//get all activities 
		def activityList = []
		def jeansList = []
		def shiftList = []
		if(currentCampaign){
			activityList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.ACTIVITY)
			jeansList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.JEANS_PAYMENT)
			shiftList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.MID_OHIO_FOOD_BANK_SHIFT)
		}
		
		//get total by each activity
		def activitySummaries = donationService.activitySummary(activityList)
		def jeansSummary = donationService.jeansSummary(jeansList)
		activitySummaries.add(jeansSummary)
			
		
		//total all the money
		def totalMoney = new BigDecimal('0')
		activitySummaries.each {
			if(it?.amount){
				totalMoney = totalMoney.add(it?.amount)
			}
		}
		
		def shiftSummary = donationService.shiftSummary(shiftList)
		activitySummaries.add(shiftSummary)
		
		//total all the meals
		def totalMeals = new BigDecimal('0')
		activitySummaries.each {
			if(it?.mealCount){
				totalMeals = totalMeals.add(it?.mealCount)
			}
		}
		
						
		returnObject.put('activitySummaries', activitySummaries)
		returnObject.put('totalMoney', totalMoney)
		returnObject.put('totalMeals', CharityChampConstants.formatter.format(totalMeals))
		
		
		
		return returnObject
		
		
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
	
	def summaryReport(){
		
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		
		}
			
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def activityList = new ArrayList()
		if(currentCampaign){
			activityList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.ACTIVITY)
		}
						
		def jeanPaymentsList = new ArrayList()
		if(currentCampaign){
			jeanPaymentsList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.JEANS_PAYMENT)
		}
		
		//totals
		def totalIncome = new BigDecimal('0')
		def totalExpense = new BigDecimal('0')
		def totalProfit = new BigDecimal('0')
		
		activityList.each {
			if(it?.amountCollected){
				totalIncome = totalIncome.add(it?.amountCollected)
			}
			if(it?.amountSpent){
				totalExpense = totalExpense.add(it?.amountSpent)
			}
			totalProfit = totalProfit.add(it?.profit)
		}
		
		jeanPaymentsList.each {
			if(it?.amountCollected){
				totalIncome = totalIncome.add(it?.amountCollected)
			}
			totalProfit = totalProfit.add(it?.profit)
		}
		
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
	
		returnModel.put('activityList', activityList)
		returnModel.put('jeanPaymentsList', jeanPaymentsList)
		returnModel.put('totalIncome', totalIncome)
		returnModel.put('totalExpense', totalExpense)
		returnModel.put('totalProfit', totalProfit)
		return returnModel
				
	}
	
	def activities() {
		
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def activityList = new ArrayList()	
		if(currentCampaign){
			activityList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.ACTIVITY)
		}
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
	
		returnModel.put('activityInstanceList', activityList)
		returnModel.put('activityInstanceTotal', activityList.size())
		
		return returnModel     
    }
	
	def foodBankShifts() {
		
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def foodBankShiftList = new ArrayList()
		if(currentCampaign){
			foodBankShiftList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.MID_OHIO_FOOD_BANK_SHIFT)
		}
		
	
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('foodBankShiftList', foodBankShiftList)
		returnModel.put('foodBankShiftListTotal', foodBankShiftList.size())
	
		
		return returnModel
		
		
	}
	
	def jeanPayments() {
		
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def jeanPaymentsList = new ArrayList()
		if(currentCampaign){
			jeanPaymentsList = donationService.donationList(currentCampaign, groupInstance, CharityChampConstants.JEANS_PAYMENT)
		}
		
	
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('jeanPaymentsList', jeanPaymentsList)
		returnModel.put('jeanPaymentsListTotal', jeanPaymentsList.size())
	
		
		return returnModel
		
		
	}
	
	def addActivity() {

		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def commonReturn = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		def activityTypes = StringList.findAll(sort:"value"){listName : CharityChampConstants.ACTIVITY_TYPE}
		def activityTypeNames = []
		activityTypes.each{activityTypeNames << it.value }
		commonReturn.put('activityTypeList', activityTypeNames)
		return commonReturn
			
	}
	
	def addFoodBankShift() {
		
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		def mealFactors = getShiftList()
				
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('mealFactorList', mealFactors)
		return returnModel
		
		
	}
	
	def addJeanPayment(){
				
		def groupId = params.id.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		commonGroupActivityReturnValues(groupInstance, currentCampaign)
				
	}
	
	def saveJeanPayment(){
		
		
		log.debug("Entering jean payment save")
		
		def jeanPaymentInstance = new JeansPayment(params)
		jeanPaymentInstance.donationDate = params.dateOfPayment
				
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('jeansPaymentInstance', jeanPaymentInstance)
			
		def depositAmount = 0
	
		if(params.amtPaid){
			try{
				def numberAmount = NumberFormat.getNumberInstance().parse(params.amtPaid)
				depositAmount = numberAmount.toBigDecimal()
			}catch(Exception ex){
				flash.message = message(code: 'activity.amount.collected.parse.exception')
				log.error("Exception occurred while formating amount collected in saveJeanPayment", ex)
				render(view: "addJeanPayment", model: returnModel)
				return
			}
		
		}
		jeanPaymentInstance.amountCollected = depositAmount
		
		boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.dateOfPayment)
		if(!donationDateIsGood){
			flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.amtPaid])
			render(view: "addJeanPayment", model: returnModel)
			return
			
		}

	
		if (!jeanPaymentInstance.save(flush :true)) {
			render(view: "addJeanPayment", model: returnModel)
			return
		}
		
		DonationSource donationSource = new DonationSource()
		donationSource.donation = jeanPaymentInstance
		donationSource.orgUnit = groupInstance	
				
		if(!donationSource.save(flush : true)){
			jeanPaymentInstance.delete(flush : true)
			render(view: "addJeanPayment", model: returnModel)
			return
		}
			
		currentCampaign.addToDonationSources(donationSource).save(flush:true)
		flash.message = message(code: 'default.created.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), jeanPaymentInstance.id])
		redirect(action: "jeanPayments", id: groupInstance.id)
		
	}

	def saveActivity(){
	
		log.debug("Entering activity save")
		
		def activityInstance = new Activity(params)
		activityInstance.donationDate = params.depositDate
				
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		def activityTypes = StringList.findAll(sort:"value"){listName : CharityChampConstants.ACTIVITY_TYPE}
		def activityTypeNames = []
		activityTypes.each{activityTypeNames << it.value }
		returnModel.put('activityTypeList', activityTypeNames)
		returnModel.put('activityInstance', activityInstance)
	
		// This is needed because if not selected a String null will be passed in
		if(params.name == 'null'){
			flash.message = message(code: 'activity.name.not.selected')
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
		activityInstance.amountCollected = depositAmount
		
		boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.depositDate)
		if(!donationDateIsGood){
			flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.depositDate])
			render(view: "addActivity", model: returnModel)
			return
			
		}
		
	
		if (!activityInstance.save(flush :true)) {
			render(view: "addActivity", model: returnModel)
			return
		}
		
		DonationSource donationSource = new DonationSource()
		donationSource.donation = activityInstance
		donationSource.orgUnit = groupInstance
				
		if(!donationSource.save(flush : true)){
			activityInstance.delete(flush : true)
			render(view: "addActivity", model: returnModel)
			return
		}
			
		currentCampaign.addToDonationSources(donationSource).save(flush:true)
		flash.message = message(code: 'default.created.message', args: [message(code: 'activity.label', default: 'Activity'), activityInstance.id])
		redirect(action: "activities", id: groupInstance.id)
		
	}
	
	def editActivity(Long id) {
			
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def activityInstance = Activity.get(id)
		if (!activityInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Activity'), id])
			redirect(action: "activities", id : groupInstance.id)
			return
		}
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		def activityTypes = StringList.findAll(sort:"value"){listName : CharityChampConstants.ACTIVITY_TYPE}
		def activityTypeNames = [] 
		activityTypes.each{activityTypeNames << it.value }
		returnModel.put('activityTypeList', activityTypeNames)
		returnModel.put('activityInstance', activityInstance)

		return returnModel
	}
	
	def editJeanPayments(Long id) {
		
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def jeanPaymentInstance = JeansPayment.get(id)
		if (!jeanPaymentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), id])
			redirect(action: "jeanPayments", id : groupInstance.id)
			return
		}
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('jeansPaymentInstance', jeanPaymentInstance)
	
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
		def currentCampaign = Campaign.get(campaignId?.toLong())
			
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		def activityTypes = StringList.findAll(sort:"value"){listName : CharityChampConstants.ACTIVITY_TYPE}
		def activityTypeNames = []
		activityTypes.each{activityTypeNames << it.value }
		returnModel.put('activityTypeList', activityTypeNames)
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
		activityInstance.donationDate = params.depositDate
		
		// This is needed because if not selected a String null will be passed in
		if(params.name == 'null'){
			flash.message = message(code: 'activity.name.not.selected')
			render(view: "editActivity", model: returnModel)
			return
		}
		
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
		
		
		boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.depositDate)
		if(!donationDateIsGood){
			flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.depositDate])
			render(view: "editActivity", model: returnModel)
			return
		}
		
		
		if (!activityInstance.save(flush: true)) {
			render(view: "editActivity", model: returnModel)
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'activity.label', default: 'Activity'), activityInstance.id])
		redirect(action: "activities", id : groupInstance.id)
		
	}
	
	def deleteActivity(Long id, Long activityId){
		
			
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
		def currentCampaign = Campaign.get(campaignId?.toLong())
				
				
		try {
			def donationSource = donationService.findDonationSource(currentCampaign, activityInstance.id, CharityChampConstants.ACTIVITY)
		
			donationSource.donation = null
			donationSource.orgUnit = null
						
			activityInstance.donationSource = null			
			activityInstance.delete(flush:true)
			currentCampaign.removeFromDonationSources(donationSource)
			currentCampaign.save(flush:true)
		
			donationSource.delete(flush : true)
									
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'activity.label', default: 'Activity'), activityId])
			redirect(action: "activities", id : groupInstance.id)
			return
		}
		catch (DataIntegrityViolationException e) {
			def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
			returnModel.put('activityInstance', activityInstance)
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'activity.label', default: 'Activity'), activityId])
			render(view: "editActivity", model: returnModel)
			return
		}
			
						
		
			
	}
	
	def updateJeanPayment(Long id, Long jeanPaymentId, Long jeanPaymentVersion){
		
			def groupInstance = Group.get(id)
			if (!groupInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
				redirect(controller: "home", action: "home")
				return
			}
			
			def jeanPaymentInstance = JeansPayment.get(jeanPaymentId)
			if (!jeanPaymentInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), jeanPaymentId])
				redirect(action: "jeanPayments", id : groupInstance.id)
				return
			}
			
			def campaignId = session["campaign"]
			def currentCampaign = Campaign.get(campaignId?.toLong())
				
			def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
			returnModel.put('jeansPaymentInstance', jeanPaymentInstance)
		
			if (jeanPaymentVersion) {
				if (jeanPaymentInstance.version > jeanPaymentVersion) {
				
					jeanPaymentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
							  [message(code: 'jeanPayment.label', default: 'Jean Payment')] as Object[],
							  "Another user has updated this Jean Payment while you were editing")
					render(view: "editJeanPayments", model: returnModel)
					return
				}
			}
			
			jeanPaymentInstance.properties = params
			jeanPaymentInstance.donationDate = params.dateOfPayment
			def depositAmount = 0
			
			if(params.amtPaid){
				try{
					def numberAmount = NumberFormat.getNumberInstance().parse(params.amtPaid)
					depositAmount = numberAmount.toBigDecimal()
				}catch(Exception ex){
					flash.message = message(code: 'activity.amount.collected.parse.exception')
					log.error("Exception occurred while formating amount collected in updateJeanPayment", ex)
					render(view: "editJeanPayments", model: returnModel)
					return
				}
				jeanPaymentInstance.amountCollected = depositAmount
			}
			
			boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.dateOfPayment)
			if(!donationDateIsGood){
			
				flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.dateOfPayment])
				render(view: "editJeanPayments", model: returnModel)
				return
			}
					
			if (!jeanPaymentInstance.save(flush: true)) {
				render(view: "editJeanPayments", model: returnModel)
				return
			}
	
			flash.message = message(code: 'default.updated.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), jeanPaymentInstance.id])
			redirect(action: "jeanPayments", id : groupInstance.id)
			
		}
	
	def deleteJeanPayment(Long id, Long jeanPaymentId){
		
			
		def groupInstance = Group.get(id)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def jeanPaymentInstance = JeansPayment.get(jeanPaymentId)
		if (!jeanPaymentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), jeanPaymentId])
			redirect(action: "jeanPayments", id : groupInstance.id)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
				
				
		try {
			def donationSource = donationService.findDonationSource(currentCampaign, jeanPaymentInstance.id, CharityChampConstants.JEANS_PAYMENT)
		
			donationSource.donation = null
			donationSource.orgUnit = null
						
			jeanPaymentInstance.donationSource = null
			jeanPaymentInstance.delete(flush:true)
			currentCampaign.removeFromDonationSources(donationSource)
			currentCampaign.save(flush:true)
		
			donationSource.delete(flush : true)
									
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), jeanPaymentId])
			redirect(action: "jeanPayments", id : groupInstance.id)
			return
		}
		catch (DataIntegrityViolationException e) {
			def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
			returnModel.put('jeansPaymentInstance', jeanPaymentInstance)
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), jeanPaymentId])
			render(view: "editJeanPayments", model: returnModel)
			return
		}
			
						
		
			
	}
	
	def saveFoodBankShift(long mealFactorId){
		
		log.debug("Entering food banks shift save")
		
		def shiftInstance = new VolunteerShift(params)
						
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())

		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('volunteerShiftInstance', shiftInstance)
		def mealFactors = getShiftList()
		returnModel.put('mealFactorList', mealFactors)
	
		def mealFactor = GlobalNumericSetting.get(mealFactorId)
	
		if(!mealFactor){
			flash.message = message(code: 'volunteer.shift.type.not.found')
			render(view: "addFoodBankShift", model: returnModel)
			return
		}
		
		shiftInstance.mealFactor = mealFactor
			
		boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.donationDate)
		if(!donationDateIsGood){
			flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.donationDate])
			render(view: "addFoodBankShift", model: returnModel)
			return
			
		}

	

		if (!shiftInstance.save(flush :true)) {
			println shiftInstance.errors
			render(view: "addFoodBankShift", model: returnModel)
			return
		}
		
		DonationSource donationSource = new DonationSource()
		donationSource.donation = shiftInstance
		donationSource.orgUnit = groupInstance
				
		if(!donationSource.save(flush : true)){
			shiftInstance.delete(flush : true)
			render(view: "addFoodBankShift", model: returnModel)
			return
		}
			
		currentCampaign.addToDonationSources(donationSource).save(flush:true)
		flash.message = message(code: 'default.created.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), shiftInstance.id])
		redirect(action: "foodBankShifts", id: groupInstance.id)
		
	}
	
	def editFoodBankShift(Long id) {
		
		def groupId = params.groupId.toLong()
		def groupInstance = Group.get(groupId)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
			redirect(controller: "home", action: "home")
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
		
		def shiftInstance = VolunteerShift.get(id)
		if (!shiftInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), id])
			redirect(action: "foodBankShifts", id : groupInstance.id)
			return
		}
		
		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
		returnModel.put('volunteerShiftInstance', shiftInstance)
		def mealFactors = getShiftList()
		returnModel.put('mealFactorList', mealFactors)
	
		return returnModel
	}
	
//	def editJeanPayments(Long id) {
//		
//		def groupId = params.groupId.toLong()
//		def groupInstance = Group.get(groupId)
//		if (!groupInstance) {
//			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), groupId])
//			redirect(controller: "home", action: "home")
//			return
//		}
//		
//		def campaignId = session["campaign"]
//		def currentCampaign = Campaign.get(campaignId?.toLong())
//		
//		def jeanPayment = JeansPayment.get(id)
//		if (!jeanPayment) {
//			flash.message = message(code: 'default.not.found.message', args: [message(code: 'jeanPayment.label', default: 'Jean Payment'), id])
//			redirect(action: "jeanPayments", id : groupInstance.id)
//			return
//		}
//		
//		def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
//		returnModel.put('jeanPaymentInstance', jeanPayment)
//	
//		return returnModel
//	}
	
	def updateFoodBankShift(Long id, Long shiftId, Long shiftVersion, Long mealFactorId){
		
			def groupInstance = Group.get(id)
			if (!groupInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
				redirect(controller: "home", action: "home")
				return
			}
			
			def shiftInstance = VolunteerShift.get(shiftId)
			if (!shiftInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), shiftId])
				redirect(action: "foodBankShifts", id : groupInstance.id)
				return
			}
			
			def campaignId = session["campaign"]
			def currentCampaign = Campaign.get(campaignId?.toLong())
				
			def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
			returnModel.put('volunteerShiftInstance', shiftInstance)
			def mealFactors = getShiftList()
			returnModel.put('mealFactorList', mealFactors)
			
			if (shiftVersion) {
				if (shiftInstance.version > shiftVersion) {
				
					shiftInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
							  [message(code: 'volunteerShift.label', default: 'Volunteer Shift')] as Object[],
							  "Another user has updated this Volunteer Shift while you were editing")
					render(view: "editFoodBankShift", model: returnModel)
					return
				}
			}
			
			shiftInstance.properties = params
			
			def mealFactor = GlobalNumericSetting.get(mealFactorId)
			
			if(!mealFactor){
				flash.message = message(code: 'volunteer.shift.type.not.found')
				render(view: "editFoodBankShift", model: returnModel)
				return
			}
				
			shiftInstance.mealFactor = mealFactor
						
			boolean donationDateIsGood = CharityChampUtils.donationOccursWithinValidCampaign(currentCampaign, params.donationDate)
			if(!donationDateIsGood){
				flash.message = message(code: 'donation.date.not.in.valid.campaign', args: [params.donationDate])
				render(view: "editFoodBankShift", model: returnModel)
				return
			}
			
			
			if (!shiftInstance.save(flush: true)) {
				render(view: "editFoodBankShift", model: returnModel)
				return
			}
	
			flash.message = message(code: 'default.updated.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), shiftInstance.id])
			redirect(action: "foodBankShifts", id : groupInstance.id)
			
		}
	
	def deleteFoodBankShift(Long id, Long shiftId){
		
			
		def groupInstance = Group.get(id)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
			redirect(controller: "home", action: "home")
			return
		}
		
		def shiftInstance = VolunteerShift.get(shiftId)
		if (!shiftInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), shiftId])
			redirect(action: "foodBankShifts", id : groupInstance.id)
			return
		}
		
		def campaignId = session["campaign"]
		def currentCampaign = Campaign.get(campaignId?.toLong())
				
				
		try {
			def donationSource = donationService.findDonationSource(currentCampaign, shiftInstance.id, CharityChampConstants.MID_OHIO_FOOD_BANK_SHIFT)
		
			donationSource.donation = null
			donationSource.orgUnit = null
						
			shiftInstance.donationSource = null
			shiftInstance.delete(flush:true)
			currentCampaign.removeFromDonationSources(donationSource)
			currentCampaign.save(flush:true)
		
			donationSource.delete(flush : true)
									
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), shiftId])
			redirect(action: "foodBankShifts", id : groupInstance.id)
			return
		}
		catch (DataIntegrityViolationException e) {
			def returnModel = commonGroupActivityReturnValues(groupInstance, currentCampaign)
			returnModel.put('volunteerShiftInstance', shiftInstance)
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'volunteerShift.label', default: 'Volunteer Shift'), shiftId])
			render(view: "editFoodBankShift", model: returnModel)
			return
		}
			
						
		
			
	}
	
	private List getShiftList(){
		
		LocalDate localDate = new LocalDate()
	//	def mealFactors = GlobalNumericSetting.findAll("from GlobalNumericSetting as g where g.mofbShift=? and g.effectiveDate<=?", [true, localDate.toDate()])
		def mealFactors = GlobalNumericSetting.createCriteria().list {
			    and {
			        eq("mofbShift", true)
			        le("effectiveDate", localDate.toDate())
			    			
			    }
			    order("name", "asc")
			}
			
	}
	
}
