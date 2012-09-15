package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Group)
class GroupSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(Group, [new Group()])
    }

    @Unroll("test Group all constraints #field is #error")
    def "test Group all constraints"() {
        when:
        def obj = new Group("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field                 | val
        'blank'                | 'name'                | ''
        'nullable'             | 'name'                | null
		'valid'                | 'leader'              | null
		'valid'                | 'activities'          | null
		'valid'                | 'volunteerShifts'     | null
		'valid'                | 'jeansPayment'        | null
		
			
    }

}
