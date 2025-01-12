package fr.pantheonsorbonne.exception;

public class PaperNotFoundException extends Exception {
    public PaperNotFoundException(Long id) {
        super("Paper with id " + id + " not found");
    }
}
