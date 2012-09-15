package com.charitychamp

class Business {

	String name
	Person executive
	Person charityCaptain
	
	static hasMany = [departments: Department]
	
    static constraints = {
		name blank : false
		departments nullable : true
    }
}
