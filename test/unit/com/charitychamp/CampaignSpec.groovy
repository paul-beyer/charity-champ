package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Campaign)
class CampaignSpec extends ConstraintUnitSpec{

     def setup() {
    
        mockForConstraintsTests(Campaign, [new Campaign(name : 'First')])
    }

    @Unroll("test Campaign all constraints #field is #error")
    def "test Campaign all constraints"() {
        when:
        def obj = new Campaign("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field              | val
    
        'blank'                | 'name'             | ''
        'nullable'             | 'name'             | null
		'unique'               | 'name'             | 'First'
		'nullable'             | 'startDate' 		| null
		'nullable'             | 'endDate'  		| null
		'valid'                | 'donationSources'  | null
		
			
    }

}
