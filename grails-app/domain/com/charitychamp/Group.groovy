package com.charitychamp

import java.util.Date;

class Group {

	String name
	Person leader
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [activities: Activity, volunteerShifts : VolunteerShift, jeansPayments : JeansPayment]
	
    static constraints = {
		name blank : false
		leader nullable : true
		activities nullable : true
		volunteerShifts nullable : true
		jeansPayments nullable : true
    }
}
