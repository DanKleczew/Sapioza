package fr.pantheonsorbonne.exception;

import fr.pantheonsorbonne.enums.Cause;

public class PaperOwnershipDeniedException extends Exception {

    private final Cause cause;

    public PaperOwnershipDeniedException(Cause cause) {
        super("Paper ownership denied");
        this.cause = cause;
    }

    public Cause getReason() {
        return cause;
    }
}
