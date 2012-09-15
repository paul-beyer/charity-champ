package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Activity)
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
		'nullable'             | 'depositDate'      | null
			
    }

}
