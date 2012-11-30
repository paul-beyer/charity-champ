package com.charitychamp

class AdminController {

    def admin() { }
	
	def people() { }
	
	def configure() { }
	
	def setup() { }
	
	def changeCampaign() { 
		
		def sessionCampaignId = session["campaign"]
		def campaign = Campaign.get(sessionCampaignId.toLong())
		[currentCampaignId : campaign.id]
//		if(!sessionCampaignId){
//			def dateToday = dateHandlerService.todaysDate()
//			def currentCampaign = CharityChampUtils.currentCampaign(dateToday)
//			session["campaign"] = currentCampaign.id
	}
	
	def saveChangeCampaign() {
		
		def campaign = Campaign.get(params.overrideCampaign.id.toLong())
		session["campaign"] = campaign.id
		redirect(controller: "home")
	}
}
