package org.serratec.ecommerce.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class CPFViolationException extends DataIntegrityViolationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1347351731860698064L;

	public CPFViolationException(String msg) {
        super(msg);
    }

    public CPFViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}