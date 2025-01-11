package fr.pantheonsorbonne.exception;

/**
 * Exception générique pour les erreurs liées aux notifications.
 */
public class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
