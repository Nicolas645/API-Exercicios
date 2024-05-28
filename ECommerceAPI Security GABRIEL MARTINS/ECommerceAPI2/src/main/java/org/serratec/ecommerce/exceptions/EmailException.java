package org.serratec.ecommerce.exceptions;

public class EmailException extends RuntimeException {

	private static final long serialVersionUID = -7573831960590217486L;

	public EmailException(String message) {
		super(message);
	}

}
