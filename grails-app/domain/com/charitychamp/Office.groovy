package com.charitychamp

class Office {

	String name
	Person officer
	Person charityLeader
	String teamNumber
	
	static hasMany = [businesses: Business]
	
    static constraints = {
		name blank:false
		businesses nullable : true
		teamNumber blank : false
    }
}
