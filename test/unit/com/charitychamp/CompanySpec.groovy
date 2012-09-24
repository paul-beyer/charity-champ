package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Company)
class CompanySpec extends ConstraintUnitSpec{

     def setup() {
       
        mockForConstraintsTests(Company, [new Company()])
    }

    @Unroll("test Company all constraints #field is #error")
    def "test Company all constraints"() {
        when:
        def obj = new Company("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field              | val
        'blank'                | 'name'             | ''
        'nullable'             | 'name'             | null
		'nullable'             | 'ceo'              | null
		'valid'                | 'businesses'       | null
		'valid'                | 'globalSettings'   | null
		
			
    }

}

