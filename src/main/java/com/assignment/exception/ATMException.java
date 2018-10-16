package com.assignment.exception;

/**
 * 
 * @author shaaf
 *This class Converts exception into user defined used exception
 */
public class ATMException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7737077668703681807L;
	
	private final ErrorCode code;
	
	public ATMException (ErrorCode code) {
		super();
		this.code = code;
	}
	
    public ATMException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
    
    public ErrorCode getCode() {
        return this.code;
    }

}
