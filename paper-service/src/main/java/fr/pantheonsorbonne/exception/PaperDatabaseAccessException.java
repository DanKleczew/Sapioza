package fr.pantheonsorbonne.exception;

public class PaperDatabaseAccessException extends RuntimeException {
    public PaperDatabaseAccessException() {
        super("There was an error trying to access the database");
    }
}
