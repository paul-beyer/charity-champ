package com.charitychamp

class HomeController {

	def dateHandlerService
	
    def index() { }
	
	def home(){
		
		def sessionCampaignId = session["campaign"]
		if(!sessionCampaignId){
			def dateToday = dateHandlerService.todaysDate()
			def currentCampaign = CharityChampUtils.currentCampaign(dateToday)
			session["campaign"] = currentCampaign.id
		}
		
	}
}
