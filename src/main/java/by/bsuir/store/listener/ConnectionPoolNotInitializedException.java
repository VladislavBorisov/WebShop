package by.bsuir.store.listener;

public class ConnectionPoolNotInitializedException extends RuntimeException {
    public ConnectionPoolNotInitializedException() {
        super();
    }

    public ConnectionPoolNotInitializedException(String message) {
        super(message);
    }

    public ConnectionPoolNotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolNotInitializedException(Throwable cause) {
        super(cause);
    }
}
