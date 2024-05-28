package org.serratec.ecommerce.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class MyDataIntegrityViolationException extends DataIntegrityViolationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -825477053716297105L;

	public MyDataIntegrityViolationException(String msg) {
        super(msg);
    }

    public MyDataIntegrityViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
