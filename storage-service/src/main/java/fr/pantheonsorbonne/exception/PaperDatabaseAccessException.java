package fr.pantheonsorbonne.exception;

public class PaperDatabaseAccessException extends RuntimeException {
  public PaperDatabaseAccessException(String message) {
    super(message);
  }
}
