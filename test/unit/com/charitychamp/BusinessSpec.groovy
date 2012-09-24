package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Business)
class BusinessSpec extends ConstraintUnitSpec{

     def setup() {
    
        mockForConstraintsTests(Business, [new Business()])
    }

    @Unroll("test Business all constraints #field is #error")
    def "test Business all constraints"() {
        when:
        def obj = new Business("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field              | val
        'blank'                | 'name'             | ''
        'nullable'             | 'name'             | null
		'nullable'             | 'executive'        | null
		'nullable'             | 'charityLeader'    | null
		'valid'                | 'offices'          | null
		'blank'                | 'teamNumber'       | ''
		'nullable'             | 'teamNumber'       | null
		
			
    }

}
