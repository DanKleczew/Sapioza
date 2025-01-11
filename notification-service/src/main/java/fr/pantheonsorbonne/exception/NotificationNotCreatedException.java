package fr.pantheonsorbonne.exception;

/**
 * Exception levée lorsqu'une notification n'a pas pu être créée.
 */
public class NotificationNotCreatedException extends RuntimeException {
  public NotificationNotCreatedException() {
    super("Notification could not be created.");
  }

  public NotificationNotCreatedException(String message) {
    super(message);
  }

  public NotificationNotCreatedException(String message, Throwable cause) {
    super(message, cause);
  }
}
