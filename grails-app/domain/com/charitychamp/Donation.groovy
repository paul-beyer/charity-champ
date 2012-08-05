package com.charitychamp

class Donation {

	BigDecimal amount
	String purpose 
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		amount min: 1 as BigDecimal
		purpose blank:false
    }
}
