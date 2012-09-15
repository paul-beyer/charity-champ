package com.charitychamp

class Person {
	
	String userId
	String firstName
	String lastName
	String title
	String phoneNumber
	String altPhoneNumber
	String email
	

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
