package main.java.com.finservice.exceptions;

public class DailyLimitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public DailyLimitException(double amount, String transactionType) {
		message = "You have exhausted daily transactions for " + transactionType + " of Ksh. " + amount;
	}

	public String getMessage() {
		return message;
	}
}
