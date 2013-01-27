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

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(StringList)
class StringListSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(StringList, [new StringList(listName : 'Activity Type', value : 'Bake Sale')])
    }

    @Unroll("test StringList all constraints #field is #error")
    def "test StringList all constraints"() {
        when:
        def obj = new StringList("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field           | val
        'blank'                | 'listName'      | ''
        'nullable'             | 'listName'      | null
		'blank'                | 'value'         | ''
		'nullable'             | 'value'         | null
				
					
    }
	
	def "test listName and value combination are unique"(){
		
		when:
		def obj = new StringList(listName : 'Activity Type', value : 'Bake Sale')
		
		then:
		obj.validate() == false
		obj.errors.getFieldError('value').getCode() == 'unique'
			
		
	}

}

