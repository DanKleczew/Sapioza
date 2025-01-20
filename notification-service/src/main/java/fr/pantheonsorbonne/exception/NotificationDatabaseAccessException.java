package fr.pantheonsorbonne.exception;

public class NotificationDatabaseAccessException extends NotificationException {
    public NotificationDatabaseAccessException(String message) {
        super(message);
    }

    public NotificationDatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
