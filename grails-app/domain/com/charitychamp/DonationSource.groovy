package com.charitychamp

class DonationSource {
	
	Donation donation
	OrganizationalUnit orgUnit	
	
	static belongsTo = [campaign : Campaign]
		
    static constraints = {
		donation nullable:true
		orgUnit nullable:true
		campaign nullable:true
    }
	
}
