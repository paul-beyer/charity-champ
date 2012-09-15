package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Office)
class OfficeSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(Office, [new Office()])
    }

    @Unroll("test Office all constraints #field is #error")
    def "test Office all constraints"() {
        when:
        def obj = new Office("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field           | val
        'blank'                | 'name'          | ''
        'nullable'             | 'name'          | null
		'nullable'             | 'officer'       | null
		'nullable'             | 'charityLeader' | null
		'blank'                | 'teamNumber'    | ''
		'nullable'             | 'teamNumber'    | null
		'valid'                | 'businesses'    | null
					
    }

}

