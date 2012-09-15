package com.charitychamp

class Company {

	String name
	Person ceo
	
	static hasMany = [offices : Office, globalSettings : GlobalNumericSetting]
	
    static constraints = {
		name blank : false
		offices nullable : true
		globalSettings nullable : true
    }
}
