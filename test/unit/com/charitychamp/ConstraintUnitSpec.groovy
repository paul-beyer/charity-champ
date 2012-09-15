package com.charitychamp

import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

abstract class ConstraintUnitSpec extends Specification{

    String getLongString(Integer length) {
        def longString = ""
        length.times { longString += "a" }
        longString
    }
	
	String getEmail(Boolean valid) {
		valid ? "plbeyer70@gmail.com" : "plbeyer70@g"
	}

    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors[field]
            assert error == obj.errors[field]
        } else {
            assert !obj.errors[field]
        }
    }
}
