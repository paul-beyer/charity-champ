package com.charitychamp

import java.util.Date;

class Office {

	String name
	Person officer
	Person charityCaptain
	
	Date dateCreated
	Date lastUpdated
	
	
	static hasMany = [departments: Department]
	
    static constraints = {
		name blank:false
		departments nullable : true
		
    }
}
