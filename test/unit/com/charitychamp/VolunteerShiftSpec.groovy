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
@TestFor(VolunteerShift)
class VolunteerShiftSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(VolunteerShift, [new VolunteerShift()])
    }

    @Unroll("test VolunteerShift all constraints #field is #error")
    def "test VolunteerShift all constraints"() {
        when:
        def obj = new VolunteerShift("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field                | val
        'nullable'             | 'donationDate'        | ''
        'min'                  | 'numberOfVolunteers' | 0
		'valid'                | 'comments'           | null
		'valid'                | 'leader'             | null
		'nullable'             | 'mealFactor'         | null
    }

}

