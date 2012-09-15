package com.charitychamp

class VolunteerShift {
	
	Date dateOfShift
	int numberOfVolunteers
	String comments
	Person leader
	BigDecimal mealFactor
	

    static constraints = {
		numberOfVolunteers min : 1
		comments nullable : true
		leader nullable : true
		mealFactor min : 1 as BigDecimal
    }
}
