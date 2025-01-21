package fr.pantheonsorbonne.exception;

public class PaperNotFoundException extends Exception {
    public PaperNotFoundException(Long id) {
        super("Paper with id " + id + " not found");
    }
    public PaperNotFoundException(String uuid) {
        super("Paper with uuid " + uuid + " not found");
    }
}
