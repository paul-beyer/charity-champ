/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.charitychamp

import java.util.Date;

class JeansPayment extends Donation{
	
	static final String type = CharityChampConstants.jeansPayment
	
	String employeeUserId
	String payerFirstName
	String payerLastName
	String payerPhone
	String payerEmail
	BigDecimal amtPaid

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
