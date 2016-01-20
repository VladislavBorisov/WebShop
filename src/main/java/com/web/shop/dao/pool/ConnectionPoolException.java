package com.web.shop.dao.pool;

public class ConnectionPoolException extends Exception {
	private static final long serialVersionUID = -336380388011162356L;

	public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }
}
