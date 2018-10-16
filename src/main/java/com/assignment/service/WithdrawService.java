package com.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.constants.ATMConstants;
import com.assignment.exception.ATMException;
import com.assignment.exception.ErrorCode;
import com.assignment.objects.ResponseObject;
import com.assignment.utility.ATMUtil;

/**
 * this service class has computation logic specific to withdrawals only
 * @author shaaf
 *
 */
@Service("withdrawService")
public class WithdrawService {
	
	private static final Logger logger = LoggerFactory.getLogger(WithdrawService.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthService authService;
	

	/**
	 * denominations array hold the allowed/available denominations
	 * quantity array hold their respective quantities at respective index position
	 * used int as it's faster in processing than Integer
	 */
	int[] denominations = { 20, 50, 500 };
	int[] quantity = { 20, 20, 1 };
	private int[] leastNotesCombination;

	public ResponseObject withdrawAmount(int withdrawAmount) throws ATMException {
		ResponseObject response = new ResponseObject();
		response.setSuccess(Boolean.TRUE);
		if (authService.isUserAuthorize()) {
			try {
				if (withdrawAmount > ATMConstants.MAX_AMOUNT_TO_WITHDRAW) {
					logger.debug("Cannot withdraw more than {},{}", ATMConstants.CURRENY_SYMBOL, ATMConstants.MAX_AMOUNT_TO_WITHDRAW);
					response.setSuccess(Boolean.FALSE);
					response.setErrorMessage(ATMConstants.MAX_WITHDRAWL_AMOUNT_LIMIT_EXCEEDED);
				} else {
					if (!isValidwithdrawAmount(withdrawAmount)) {
						logger.warn(ATMConstants.INVALID_AMOUNT);
						response.setSuccess(Boolean.FALSE);
						response.setErrorMessage(ATMConstants.INVALID_AMOUNT);
					} else {
						quantitiesOfDenominations(withdrawAmount, response);
						response.setDispensedCurrencyString(ATMUtil.transformResponse(response));
					}
				}
			} catch(Exception e) {
				throw new ATMException(ErrorCode.ERROR_OCCURED);
			} finally {
				response.setWithdrawAmount(withdrawAmount);
				response.setBalanceLeftInATM(getATMBalance());
			}
		}
			return response;
	}

	/**
	 * this method returns the combination of denominations and quantities which dispense least amount of notes
	 * @param withdrawAmount
	 * @param response
	 * @return
	 */
	private ResponseObject quantitiesOfDenominations(int withdrawAmount, ResponseObject response) {
		if (isATMhasSufficientBalance(withdrawAmount)) {
			leastNotesCombination = null; 
			solutions(denominations, quantity, new int[denominations.length], withdrawAmount, 0);
			if (leastNotesCombination != null && leastNotesCombination.length > 0) {
				updateRemainingDenominations(leastNotesCombination);
				response.setDenomination(denominations);
				response.setQuantity(quantity);
				userService.updateUserBalance(withdrawAmount);
				response.setDispensedCurrencyString(ATMUtil.transformResponse(response));
				response.setDispensedNotes(leastNotesCombination);
			} else {
				logger.warn(ATMConstants.ENTER_VALID_AMOUNT);
				response.setSuccess(Boolean.FALSE);
				response.setErrorMessage(ATMConstants.ENTER_VALID_AMOUNT);
			}
		} else {
			logger.debug(ATMConstants.NOT_ENOUGH_CASH, ATMConstants.ENTER_VALID_AMOUNT);
			response.setSuccess(Boolean.FALSE);
			response.setErrorMessage(ATMConstants.NOT_ENOUGH_CASH);
		}
		return response;
	}

	/**
	 * this method is a recursive method which calculates all possible combinations which can be used to dispense the given withdrawal amount
	 * @param denomination
	 * @param quantity
	 * @param variation
	 * @param withdrawAmount
	 * @param position
	 * @return
	 */
	private List<Integer[]> solutions(int[] denomination, int[] quantity, int[] variation, int withdrawAmount, int position) {
		List<Integer[]> list = new ArrayList<>();
		int value = compute(denomination, variation);
		if (value < withdrawAmount) {
			for (int i = position; i < denomination.length; i++) {
				if (quantity[i] > variation[i]) {
					int[] newvariation = variation.clone();
					newvariation[i]++;
					List<Integer[]> newList = solutions(denomination, quantity, newvariation, withdrawAmount, i);
					if (newList != null) {
						list.addAll(newList);
					}
				}
			}
		} else if (value == withdrawAmount) {
			IntStream.of(variation).sum();
			list.add(ATMUtil.convertToInteger(variation));
			logCombination(variation);
			if (leastNotesCombination == null) {
				leastNotesCombination = variation;
			}
			if (IntStream.of(leastNotesCombination).sum() > IntStream.of(variation).sum()) {
				leastNotesCombination = variation;
			}
		}
		return list;
	}

	/**
	 * this is a helper method of 'solutions' method
	 * @param values
	 * @param variation
	 * @return
	 */
	private int compute(int[] values, int[] variation) {
		int ret = 0;
		for (int i = 0; i < variation.length; i++) {
			ret += values[i] * variation[i];
		}
		return ret;
	}

	/**
	 * this method updates the remaining quantity of notes for each denominations
	 * @param leastCashCombination
	 */
	private void updateRemainingDenominations(int[] leastCashCombination) {
		for (int i = 0; i < quantity.length; i++) {
			if (leastCashCombination[i] > 0) {
				quantity[i] = quantity[i] - leastCashCombination[i];
			}
		}
	}

	/**
	 * this method checks if ATM has sufficient amount to dispense
	 * @param withdrawAmount
	 * @return
	 */
	private boolean isATMhasSufficientBalance(int withdrawAmount) {
		return getATMBalance() >= withdrawAmount ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * this method calculates and logs the amount left in the ATM
	 * @return
	 */
	private int getATMBalance() {
		logger.info("Calculating balance amount left in ATM");
		int balance = 0;
		for (int i = 0; i < denominations.length; i++) {
			if (quantity[i] > 0) {
				balance = balance + denominations[i] * quantity[i];
			}
		}
		logger.info("Total balance in ATM $ {}", balance);
		return balance;
	}
	
	/**
	 * this method checks if amount is greater than zero, can also have other logic to check if ATM can dispense the amount entered by the user
	 * @param withdrawAmount
	 * @return
	 */
	private boolean isValidwithdrawAmount(int withdrawAmount) {
		return withdrawAmount > ATMConstants.LOWEST_DENOMINATION;
	}

	/**
	 * this method logs the various combination of denominations and their quantity for current withdrawal amount
	 * @param variation
	 */
	private void logCombination(int[] variation) {
		logger.info("[");
		for (int i =0; i < variation.length; i++) {
			if (variation[i] > 0) {
				logger.info("{},{},{}", denominations[i], "x", variation[i]);
			}
		}
		logger.info("]");
	}
	
}
