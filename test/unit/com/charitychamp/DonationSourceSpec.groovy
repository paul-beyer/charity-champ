package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(DonationSource)
class DonationSourceSpec extends ConstraintUnitSpec{

     def setup() {
    
        mockForConstraintsTests(DonationSource, [new DonationSource()])
    }

    @Unroll("test DonationSource all constraints #field is #error")
    def "test DonationSource all constraints"() {
        when:
        def obj = new DonationSource("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field              | val
    
        'nullable'             | 'donation'         | null
		'nullable'             | 'orgUnit'          | null
		
			
    }

}
