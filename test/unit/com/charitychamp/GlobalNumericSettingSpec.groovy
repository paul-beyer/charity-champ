package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(GlobalNumericSetting)
class GlobalNumericSettingSpec extends ConstraintUnitSpec{

     def setup() {
        
        mockForConstraintsTests(GlobalNumericSetting, [new GlobalNumericSetting()])
    }

    @Unroll("test GlobalSetting all constraints #field is #error")
    def "test GlobalSetting all constraints"() {
        when:
        def obj = new GlobalNumericSetting("$field": val)

        then:
        validateConstraints(obj, field, error)

        where:
        error                  | field                 | val
        'blank'                | 'name'                | ''
        'nullable'             | 'name'                | null
		'nullable'             | 'effectiveDate'       | null
		'nullable'             | 'value'               | null
		'min'                  | 'value'               | 0
		
			
    }

}
