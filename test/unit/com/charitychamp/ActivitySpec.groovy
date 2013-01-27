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

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.joda.time.LocalDate
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Activity)
@Mock([GlobalNumericSetting, Activity])
class ActivitySpec extends ConstraintUnitSpec{

     def setup() {
    
        mockForConstraintsTests(Activity, [new Activity()])
    }

    @Unroll("test Activty all constraints #field is #error")
    def "test Activity all constraints"() {
        when:
        def obj = new Activity("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field              | val
        'blank'                | 'name'             | ''
        'nullable'             | 'name'             | null
		'min'                  | 'amountCollected'  | 0
		'nullable'             | 'amountCollected'  | null
		'nullable'             | 'donationDate'      | null
			
    }
	
	def "test getting number of meals from Activity"(){
		
		when:
		def donationDate = new LocalDate(2012,1,1)
		def mealsADollarBuysDate = new LocalDate(2012,1,1)
		def activity = new Activity(name : "Some activity", donationDate : donationDate.toDate(), amountCollected : new BigDecimal('56.78'))
		def mealsADollarBuys = new GlobalNumericSetting(name : "Meals a Dollar Buys", mofbShift : false, value : new BigDecimal('33.3'), effectiveDate: mealsADollarBuysDate.toDate()).save()
		def numberOfMeals = activity.numberOfMeals
	
		then:
		numberOfMeals.compareTo(new BigDecimal('1890.77')) == 0
		
		
	}
	
	def "test getting number of meals from Activity when no dollar multiplier found"(){
		
		when:
		def donationDate = new LocalDate(2011,12, 31)
		def mealsADollarBuysDate = new LocalDate(2012,1,1)
		def activity = new Activity(name : "Some activity", donationDate : donationDate.toDate(), amountCollected : new BigDecimal('56.78'))
		def mealsADollarBuys = new GlobalNumericSetting(name : "Meals a Dollar Buys", mofbShift : false, value : new BigDecimal('33.3'), effectiveDate: mealsADollarBuysDate.toDate()).save()
		def numberOfMeals = activity.numberOfMeals
				
		then:
		numberOfMeals.compareTo(new BigDecimal('0')) == 0
		
		
	}
	
	
}
