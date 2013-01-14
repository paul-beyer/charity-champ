package com.charitychamp

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate

class GlobalNumericSettingService {
	
	private static final employeeGoalName = 'Goal Amount Per Employee'

	//This method determines if there is another GLobalNumericSetting with the same name and the same effective date
    def boolean determineIfSettingDateHasBeenUsed(String name, Date newDate) {
		
		def hasBeenUsed = false
		if(name && newDate){
		
			def globals = GlobalNumericSetting.createCriteria().list {
				and {
					eq("name", name)
					eq("effectiveDate", newDate)
							
				}
			}
			
			if(globals.size() > 0){
				hasBeenUsed = true
			}
		}
		return hasBeenUsed

    }
	
	def boolean validateFoodBankShift(String name, Date newDate){
		
		def valid = true
		if(StringUtils.equalsIgnoreCase(name, 'Meals a Dollar Buys') || StringUtils.equalsIgnoreCase(name, 'Goal Amount Per Employee')){
			valid = false
		}
		return valid
	}
}
