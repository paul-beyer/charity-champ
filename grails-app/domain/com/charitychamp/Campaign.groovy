package com.charitychamp

class Campaign {
	
	String name
	Date startDate
	Date endDate
		
	static hasMany = [donationSources : DonationSource]
	
    static constraints = {
		name blank:false, unique:true
		donationSources nullable:true
    }
	
	@Override
	public String toString(){
		
	
		return "${name} ${startDate.toString().substring(0,10)} - ${endDate.toString()substring(0,10)}"
		
	}
}
