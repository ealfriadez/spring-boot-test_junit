package com.ntloc.test_junit.exception;

public class CustomerEmailUnavailableException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerEmailUnavailableException(String message) {
        super(message);
    }
}
