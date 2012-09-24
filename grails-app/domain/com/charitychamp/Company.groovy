package com.charitychamp

class Company {

	String name
	Person ceo
	
	static hasMany = [businesses : Business, globalSettings : GlobalNumericSetting]
	
    static constraints = {
		name blank : false
		businesses nullable : true
		globalSettings nullable : true
    }
}
