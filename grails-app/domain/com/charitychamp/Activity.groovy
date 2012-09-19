package com.charitychamp


class Activity {

	String name
	BigDecimal amountCollected
	String leaderName
	String comments
	Date depositDate
	
	Date dateCreated
	Date lastUpdated
	
	
    static constraints = {
		name blank:false
		amountCollected min : 1 as BigDecimal
		leaderName nullable : true
		comments nullable : true
		depositDate blank : false
    }
}
