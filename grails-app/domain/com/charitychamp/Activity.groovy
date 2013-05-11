/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.charitychamp

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includeFields=true, callSuper=true, excludes="donationSource")
class Activity extends Donation{
	
	static final String type = CharityChampConstants.ACTIVITY

	String name
	BigDecimal amountCollected
	BigDecimal amountSpent
	String leaderName
	String comments
	
	Date dateCreated
	Date lastUpdated
	


    static constraints = {
		name blank:false
		amountCollected min : 1 as BigDecimal
		amountSpent nullable : true
		leaderName nullable : true
		comments nullable : true
				
    }
	
	public BigDecimal getNumberOfMeals(){
	
		def numberOfMealsDollarBuys = CharityChampUtils.findNumberOfMealsADollarBuys(super.getDonationDate())
		return rounded(this.amountCollected.multiply(numberOfMealsDollarBuys))
	
				
	}
	
	private BigDecimal rounded(BigDecimal aNumber){
		return aNumber.setScale(CharityChampConstants.DECIMALS, CharityChampConstants.ROUNDING_MODE);
	 }
	
	public BigDecimal getProfit(){
		
		BigDecimal profit = amountCollected
		if(amountSpent > 0){
			profit = amountCollected.subtract(amountSpent)
		}
		return profit
		
	}
	
}
