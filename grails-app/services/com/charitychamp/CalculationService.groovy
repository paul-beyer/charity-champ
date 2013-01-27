package com.charitychamp

class CalculationService {

    def sumDonations(List donations) {
		
		def sum = new BigDecimal('0')
		for(donation in donations){
			def donationAmount = donation.amountCollected
			sum = sum.add(donationAmount)
						
		}

		return sum
    }
	
	
}
