package main.java.com.finservice.exceptions;

public class InsufficientFundsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public InsufficientFundsException() {
		message = "Insufficient funds error. This account needs to be funded";
	}

	public String getMessage() {
		return message;
	}
}
