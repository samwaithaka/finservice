package main.java.com.finservice.exceptions;

public class TransactionLimitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public TransactionLimitException(double amount, String transactionType) {
		message = "Error: a " + transactionType + " transaction cannot exceed Ksh. " + amount;
	}

	public String getMessage() {
		return message;
	}
}
