package main.java.com.finservice.exceptions;

public class FrequencyLimitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public FrequencyLimitException(int frequency, String transactionType) {
		message = "You have reached maximum number of " + transactionType + "s which is " + frequency;
	}

	public String getMessage() {
		return message;
	}
}
