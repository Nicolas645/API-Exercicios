package org.serratec.ecommerce.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class EmailViolationException extends DataIntegrityViolationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1481669215488151634L;

	public EmailViolationException(String msg) {
        super(msg);
    }

    public EmailViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
