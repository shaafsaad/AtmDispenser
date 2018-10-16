package com.assignment.constants;

public final class ATMConstants {

	private ATMConstants() {}
	
	public static final int MAX_AMOUNT_TO_WITHDRAW = 500;
	public static final int LOWEST_DENOMINATION = 10;
	
	public static final String CURRENY_SYMBOL = "$";
	public static final String MAX_WITHDRAWL_AMOUNT_LIMIT_EXCEEDED  = "Maximum withdrawal limit per transaction is " + CURRENY_SYMBOL + MAX_AMOUNT_TO_WITHDRAW;
	public static final String ENTER_VALID_AMOUNT = "ATM cannot dispense this Amount, Please choose another Amount";
	public static final String NOT_ENOUGH_CASH = "Sorry not enough cash";
}
