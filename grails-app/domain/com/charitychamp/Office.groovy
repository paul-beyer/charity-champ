package com.charitychamp

class Office {

	String name
	Person officer
	Person charityCaptain
	
	
	static hasMany = [departments: Department]
	
    static constraints = {
		name blank:false
		departments nullable : true
		
    }
}
