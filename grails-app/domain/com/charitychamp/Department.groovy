package com.charitychamp

class Department {
	
	String name
	Person departmentHead
	Person charityLieutenant
	int numberOfEmployees
	Date dateOfEmployeeCount
	
	static hasMany = [groups: Group]
	
    static constraints = {
		name blank : false
		numberOfEmployees nullable : true
		dateOfEmployeeCount nullable : true
		
    }
}
