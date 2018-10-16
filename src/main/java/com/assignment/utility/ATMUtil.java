package com.assignment.utility;

import java.util.Arrays;

import com.assignment.constants.ATMConstants;
import com.assignment.objects.ResponseObject;

/**
 * Utility class which has static methods which can be used across application
 * @author shaaf
 *
 */
public class ATMUtil {

	/**
	 * using private constructor so that it's default constructor cannot be called as it's a utility class
	 */
	private ATMUtil() {}
	
	/**
	 * this method transform cash dispensed in more readable format like [$500x1]
	 * @param response
	 * @return
	 */
	public static String transformResponse (ResponseObject response) {
		if (response.getDispensedNotes() != null && response.getDenomination().length > 0) {
			StringBuilder responseBuilder = new StringBuilder();
			responseBuilder.append("[");
			for (int i=0; i< response.getDenomination().length; i++) {
				if (response.getDispensedNotes()[i] > 0) {
					responseBuilder.append(ATMConstants.CURRENY_SYMBOL + response.getDenomination()[i] + " x " + response.getDispensedNotes()[i] + " ");
				}
			}
			responseBuilder.append("]");
			return responseBuilder.toString();
		}
		return null;
	}
	
	/**
	 * this method converts int to Integer
	 * @param ar
	 * @return
	 */
	public static Integer[] convertToInteger(int[] ar) {
		return Arrays.stream(ar).boxed().toArray(Integer[]::new);
	}
}
