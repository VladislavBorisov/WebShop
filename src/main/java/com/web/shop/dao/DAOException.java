package com.web.shop.dao;

public class DAOException extends Exception {
    /**
	 * Vladislav Borisov
	 */
	private static final long serialVersionUID = -7009005002234916482L;

	public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
