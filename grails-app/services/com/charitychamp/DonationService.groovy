package com.charitychamp

class DonationService {

    def donationList(Campaign campaign, OrganizationalUnit orgUnit, String type) {
		
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
}
