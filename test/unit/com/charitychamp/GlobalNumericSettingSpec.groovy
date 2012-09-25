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
@TestFor(GlobalNumericSetting)
class GlobalNumericSettingSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(GlobalNumericSetting, [new GlobalNumericSetting()])
    }

    @Unroll("test GlobalSetting all constraints #field is #error")
    def "test GlobalSetting all constraints"() {
        when:
        def obj = new GlobalNumericSetting("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field                 | val
        'blank'                | 'name'                | ''
        'nullable'             | 'name'                | null
		'nullable'             | 'effectiveDate'       | null
		'nullable'             | 'value'               | null
		'min'                  | 'value'               | 0
		
			
    }

}
