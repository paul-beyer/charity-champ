package com.charitychamp

class ActivityService {
	
	def donationService

	def activityList(OrganizationalUnit orgUnit, Campaign campaign) {
		log.debug("Entering ActivityService.activityList")
		
		def activityList = new ArrayList()	
		if(campaign){
			activityList = donationService.donationList(campaign, orgUnit, CharityChampConstants.activity)
		}
		
		return activityList
	}
	
  
}
