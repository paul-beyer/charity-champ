package com.charitychamp

abstract class Donation {
	
	Date donationDate

	static belongsTo = [donationSource : DonationSource]
	
	static constraints = {
		donationSource nullable : true
			
	}
	
	
}
