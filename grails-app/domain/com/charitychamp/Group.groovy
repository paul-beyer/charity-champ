package com.charitychamp

class Group {

	String name
	Person leader
	
	static hasMany = [activities: Activity, volunteerShifts : VolunteerShift, jeansPayments : JeansPayment]
	
    static constraints = {
		name blank : false
		leader nullable : true
		activities nullable : true
		volunteerShifts nullable : true
		jeansPayments nullable : true
    }
}
