package com.charitychamp

import java.util.Date;

class Department {
	
	String name
	Person departmentHead
	Person charityLieutenant
	int numberOfEmployees
	Date dateOfEmployeeCount
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [groups: Group]
	
    static constraints = {
		name blank : false
		numberOfEmployees nullable : true
		dateOfEmployeeCount nullable : true
		
    }
}
