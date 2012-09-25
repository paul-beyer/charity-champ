package com.charitychamp

import java.util.Date;

class VolunteerShift {
	
	Date dateOfShift
	int numberOfVolunteers
	String comments
	Person leader
	BigDecimal mealFactor
	
	Date dateCreated
	Date lastUpdated
	

    static constraints = {
		numberOfVolunteers min : 1
		comments nullable : true
		leader nullable : true
		mealFactor min : 1 as BigDecimal
    }
}
