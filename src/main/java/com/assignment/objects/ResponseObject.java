package com.assignment.objects;

/**
 * this class wraps the response
 * @author shaaf
 *
 */
public class ResponseObject {
	
	boolean success;
	int[] denomination;
	int[] quantity;
	int[] dispensedNotes;
	int balanceLeftInATM;
	private String dispensedCurrencyString;
	private int withdrawAmount;
	private String errorMessage;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int[] getDenomination() {
		return denomination;
	}
	public void setDenomination(int[] denomination) {
		this.denomination = denomination;
	}
	public int[] getQuantity() {
		return quantity;
	}
	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}
	public int[] getDispensedNotes() {
		return dispensedNotes;
	}
	public void setDispensedNotes(int[] dispensedNotes) {
		this.dispensedNotes = dispensedNotes;
	}
	public int getBalanceLeftInATM() {
		return balanceLeftInATM;
	}
	public void setBalanceLeftInATM(int balanceLeftInATM) {
		this.balanceLeftInATM = balanceLeftInATM;
	}
	public String getDispensedCurrencyString() {
		return dispensedCurrencyString;
	}
	public void setDispensedCurrencyString(String dispensedCurrencyString) {
		this.dispensedCurrencyString = dispensedCurrencyString;
	}
	public int getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(int withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
