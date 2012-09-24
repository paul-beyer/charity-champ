package com.charitychamp

class Business {

	String name
	Person executive
	Person charityLeader
	String teamNumber
	
	static hasMany = [offices: Office]
	
    static constraints = {
		
		name blank : false
		teamNumber blank : false
		offices nullable : true
		
    }
}
