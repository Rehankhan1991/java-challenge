package jp.co.axa.apidemo.exceptions;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186619695588871744L;

	public ValidationException(String message) {
		super(message);
	}
}
