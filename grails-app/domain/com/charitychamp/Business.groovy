package com.charitychamp

import java.util.Date;

class Business {

	String name
	Person executive
	Person charityLeader
	String teamNumber
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [offices: Office]
	
    static constraints = {
		
		name blank : false
		teamNumber blank : false
		offices nullable : true
		
    }
}
