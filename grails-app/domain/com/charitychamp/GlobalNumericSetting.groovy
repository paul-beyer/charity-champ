package com.charitychamp

import java.util.Date;

class GlobalNumericSetting {

	String name
	Date effectiveDate
	BigDecimal value
	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		name blank : false
		value min : 1 as BigDecimal
    }
}
