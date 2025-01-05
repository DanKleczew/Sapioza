package fr.pantheonsorbonne.exception;

public class PaperNotFoundException extends Exception {
    public PaperNotFoundException(long id) {
        super("Paper with id " + id + " not found");
    }
}
