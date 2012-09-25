package com.charitychamp

import java.util.Date;

class Company {

	String name
	Person ceo
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [businesses : Business, globalSettings : GlobalNumericSetting]
	
    static constraints = {
		name blank : false
		businesses nullable : true
		globalSettings nullable : true
    }
}
