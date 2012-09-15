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
        
        mockForConstraintsTests(StringList, [new StringList()])
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

}

