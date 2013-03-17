package com.charitychamp

import java.text.NumberFormat

class CharityChampConstants {

	public static final String ACTIVITY = "activity"
	public static final String JEANS_PAYMENT = "jeansPayment"
	public static final String MID_OHIO_FOOD_BANK_SHIFT = "mofbShift"
	public static final String GOAL_PER_EMPLOYEE_NAME_VALUE = 'Goal Amount Per Employee'
	public static final String MEALS_A_DOLLAR_BUYS_NAME_VALUE = 'Meals a Dollar Buys'
	public static final String JEANS = "Jeans"
	public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	public static final int PERCENTAGE = new BigDecimal('100')
	public static final int DECIMALS = 2;
	public static final String ACTIVITY_TYPE = "Activity Type"
	public static final NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));
	
}
