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
