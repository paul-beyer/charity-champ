package com.charitychamp

import java.util.Date;

class Person {
	
	String userId
	String firstName
	String lastName
	String title
	String phoneNumber
	String altPhoneNumber
	String email
	
	Date dateCreated
	Date lastUpdated
	

    static constraints = {
		userId blank:false, unique : true
		firstName blank:false
		lastName blank:false
		phoneNumber nullable:true
		altPhoneNumber nullable:true
		email email:true, nullable:true
		title nullable:true
    }
}
