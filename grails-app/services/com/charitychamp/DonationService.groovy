package com.charitychamp

class DonationService {

    def List donationList(Campaign campaign, OrganizationalUnit orgUnit, String type) {
		
		log.debug("Entering donationsList")
		def donationSources = campaign.donationSources
		def foundDonations = new ArrayList()
		if(donationSources){
			donationSources.each {
				if(it.donation?.type == type && it.orgUnit?.name == orgUnit.name){
					foundDonations << it.donation
				}	
				
			}

		}
		def sortedDonations = foundDonations.sort{it.id}
		return sortedDonations
		

    }
	
	def DonationSource findDonationSource(Campaign campaign, Long donationId, String type){
		
		log.debug("Entering findDonatinWithinCampaign")
	
		def foundDonation = null
		
		def donationSources = campaign.donationSources
		if(donationSources){
			donationSources.each {
			
					if(it.donation?.id == donationId  && it.donation?.type == type){
						foundDonation = it
						return foundDonation
					}
				
			}
		}
		
		return foundDonation
	}
}
