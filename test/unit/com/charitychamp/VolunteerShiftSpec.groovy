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
        'nullable'             | 'dateOfShift'        | ''
        'min'                  | 'numberOfVolunteers' | 0
		'valid'                | 'comments'           | null
		'valid'                | 'leader'             | null
		'min'                  | 'mealFactor'         | 0		
		'nullable'             | 'mealFactor'         | null
    }

}

