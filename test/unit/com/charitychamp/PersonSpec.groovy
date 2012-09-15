package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Person)
class PersonSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(Person, [new Person(userId:'beyerp')])
    }

    @Unroll("test Person all constraints #field is #error")
    def "test Person all constraints"() {
        when:
        def obj = new Person("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field        | val
        'blank'                | 'userId'     | ''
        'nullable'             | 'userId'     | null
		'unique'               | 'userId'     | 'beyerp'
		'blank'                | 'firstName'  | ''
		'nullable'             | 'firstName'  | null
		'blank'                | 'lastName'   | ''
		'nullable'             | 'lastName'   | null
        'email'                | 'email'      | getEmail(false)
 
    }

}
