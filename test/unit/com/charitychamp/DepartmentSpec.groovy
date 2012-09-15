package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Department)
class DepartmentSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(Department, [new Department()])
    }

    @Unroll("test Department all constraints #field is #error")
    def "test Department all constraints"() {
        when:
        def obj = new Department("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field                 | val
        'blank'                | 'name'                | ''
        'nullable'             | 'name'                | null
		'nullable'             | 'departmentHead'      | null
		'nullable'             | 'charityLieutenant'   | null
		'valid'                | 'numberOfEmployees'   | null
		'valid'                | 'dateOfEmployeeCount' | null	
			
    }

}


