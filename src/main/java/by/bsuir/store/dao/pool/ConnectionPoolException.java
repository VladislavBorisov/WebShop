package by.bsuir.store.dao.pool;

public class ConnectionPoolException extends Exception {
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
