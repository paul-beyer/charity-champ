package com.charitychamp

class GlobalNumericSetting {

	String name
	Date effectiveDate
	BigDecimal value
	
    static constraints = {
		name blank : false
		value min : 1 as BigDecimal
    }
}
