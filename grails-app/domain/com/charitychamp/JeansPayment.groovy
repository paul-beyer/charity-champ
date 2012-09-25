package com.charitychamp

import java.util.Date;

class JeansPayment {
	
	String employeeUserId
	String payerFirstName
	String payerLastName
	String payerPhone
	String payerEmail
	BigDecimal amtPaid
	Date dateOfPayment
	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		employeeUserId blank : false
		payerFirstName blank : false
		payerLastName blank : false
		payerPhone nullable : true
		payerEmail nullable : true, email : true
		amtPaid min : 1 as BigDecimal
		
    }
}
