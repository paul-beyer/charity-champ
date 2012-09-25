package com.charitychamp

import java.util.Date;

class StringList {

	String listName
	String value
	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		listName blank : false
		value blank : false
    }
}
