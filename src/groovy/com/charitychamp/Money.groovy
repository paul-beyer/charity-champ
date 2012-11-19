package com.charitychamp

import java.math.RoundingMode

class Money {
	
	private static final Currency USD = Currency.getInstance("USD");
	private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN
	
	private BigDecimal amount
	private Currency currency

	public static Money dollars(BigDecimal amount) {
		return new Money(amount, USD)
	}

	Money(BigDecimal amount, Currency currency) {
		this(amount, currency, DEFAULT_ROUNDING)
	}

	Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
		this.amount = amount
		this.currency = currency

		this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding)
	}

	public BigDecimal getAmount() {
		return amount
	}

	public Currency getCurrency() {
		return currency
	}

	@Override
	public String toString() {
		return getCurrency().getSymbol() + " " + getAmount()
	}

	public String toString(Locale locale) {
		return getCurrency().getSymbol(locale) + " " + getAmount()
	}
}


