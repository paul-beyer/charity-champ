package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(JeansPayment)
class JeansPaymentSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(JeansPayment, [new JeansPayment()])
    }

    @Unroll("test JeansPayment all constraints #field is #error")
    def "test JeansPayment all constraints"() {
        when:
        def obj = new JeansPayment("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field                 | val
        'blank'                | 'employeeUserId'      | ''
        'nullable'             | 'employeeUserId'      | null
		'blank'                | 'payerFirstName'      | ''
		'nullable'             | 'payerFirstName'      | null
		'blank'                | 'payerLastName'       | ''
		'nullable'             | 'payerLastName'       | null
		'valid'                | 'payerPhone'          | null
		'valid'                | 'payerEmail'          | null
		'email'                | 'payerEmail'          | getEmail(false)
		'min'                  | 'amtPaid'             | 0
		'nullable'             | 'amtPaid'             | null
		'nullable'             | 'dateOfPayment'       | null
		
			
    }

}
