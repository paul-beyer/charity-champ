package com.charitychamp

class Activity {

	String name
	BigDecimal amountCollected
	Person leader
	String comments
	Date depositDate
	
    static constraints = {
		name blank:false
		amountCollected min : 1 as BigDecimal
		leader nullable : true
		comments nullable : true
		depositDate blank : false
    }
}
