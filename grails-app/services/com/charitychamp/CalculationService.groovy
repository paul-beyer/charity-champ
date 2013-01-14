package com.charitychamp

class CalculationService {

    def sumActivities(List activities) {
		
		def sum = new BigDecimal('0')
		for(activity in activities){
			def activityAmount = activity.amountCollected
			sum = sum.add(activityAmount)
						
		}

		return sum
    }
	
	def sumJeanPayments(List jeanPayments) {
		
		def sum = new BigDecimal('0')
		for(jeanPayment in jeanPayments){
			def jeanPaymentAmount = jeanPayment.amtPaid
			sum = sum.add(jeanPaymentAmount)
						
		}

		return sum
	}
}
