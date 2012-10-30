package com.charitychamp

class Campaign {
	
	String name
	Date startDate
	Date endDate
	

    static constraints = {
		name blank:false
		
    }
}
