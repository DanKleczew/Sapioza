package fr.pantheonsorbonne.exception;

/**
 * Exception levée lors d'une erreur d'accès à la base de données.
 */
public class NotificationDatabaseAccessException extends NotificationException {
    public NotificationDatabaseAccessException(String message) {
        super(message);
    }

    public NotificationDatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
