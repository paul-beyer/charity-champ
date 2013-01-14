package com.charitychamp

import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.joda.time.LocalDate
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(GlobalNumericSettingService)
@Mock(GlobalNumericSetting)
class GlobalNumericSettingServiceSpec extends UnitSpec{

    def setup() {
        // Setup logic here
    }

    def "calling determineIfSettingDateHasBeenUsed should return true if it has been used"(){
		
		when:
		def firstDate = new LocalDate(2012,1,1)
		def service = new GlobalNumericSettingService()
		def globalOne = new GlobalNumericSetting(name : CharityChampConstants.mealsADollarBuysNameValue, effectiveDate: firstDate.toDate(), value : new BigDecimal('33'), mofbShift : false).save()
		def hasBeenUsed = service.determineIfSettingDateHasBeenUsed('Meals a Dollar Buys', firstDate.toDate())
		
		then:
        hasBeenUsed == true
    }
	
	def "calling determineIfSettingDateHasBeenUsed should return false if the dates are the same but name is different"(){
		
		when:
		def firstDate = new LocalDate(2012,1,1)
		def service = new GlobalNumericSettingService()
		def globalOne = new GlobalNumericSetting(name : CharityChampConstants.mealsADollarBuysNameValue, effectiveDate: firstDate.toDate(), value : new BigDecimal('33'), mofbShift : false).save()
		def hasBeenUsed = service.determineIfSettingDateHasBeenUsed('Some other name', firstDate.toDate())
		
		then:
		hasBeenUsed == false
	}
	
	def "calling determineIfSettingDateHasBeenUsed should return false if the dates are different but the names are the same"(){
		
		when:
		def firstDate = new LocalDate(2012,1,1)
		def secondDate = new LocalDate(2012,1,2)
		def service = new GlobalNumericSettingService()
		def globalOne = new GlobalNumericSetting(name : CharityChampConstants.mealsADollarBuysNameValue, effectiveDate: firstDate.toDate(), value : new BigDecimal('33'), mofbShift : false).save()
		def hasBeenUsed = service.determineIfSettingDateHasBeenUsed('Meals a Dollar Buys', secondDate.toDate())
		
		then:
		hasBeenUsed == false
	}
	
	def "calling determineIfSettingDateHasBeenUsed should return false if one argument is null"(){
		
		when:
		def firstDate = new LocalDate(2012,1,1)
		def secondDate = new LocalDate(2012,1,2)
		def service = new GlobalNumericSettingService()
		def globalOne = new GlobalNumericSetting(name : CharityChampConstants.mealsADollarBuysNameValue, effectiveDate: firstDate.toDate(), value : new BigDecimal('33'), mofbShift : false).save()
		def hasBeenUsed = service.determineIfSettingDateHasBeenUsed(null, secondDate.toDate())
		
		then:
		hasBeenUsed == false
		
	}
	
	def "calling validateFoodBankShift should return false if the name is Meals a Dollar Buys"(){
		
		when:
		def firstDate = new LocalDate(2012,1,1)
		def service = new GlobalNumericSettingService()
		def valid = service.validateFoodBankShift(CharityChampConstants.mealsADollarBuysNameValue, firstDate.toDate())
		
		then:
		valid == false
		
	}
	
	def "calling validateFoodBankShift should return false if the name is Goal Amount Per Employee"(){
		
		when:
		def firstDate = new LocalDate(2012,1,1)
		def service = new GlobalNumericSettingService()
		def valid = service.validateFoodBankShift(CharityChampConstants.goalPerEmployeeNameValue, firstDate.toDate())
		
		then:
		valid == false
		
	}
	
			
	
}
