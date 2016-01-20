package com.web.shop.service;

public class ServiceException extends Exception {
    /**
	 * Vladislav Borisov
	 */
	private static final long serialVersionUID = -9141340455892598865L;

	public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
