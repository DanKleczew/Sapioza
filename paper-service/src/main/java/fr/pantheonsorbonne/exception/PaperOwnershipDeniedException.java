package fr.pantheonsorbonne.exception;

public class PaperOwnershipDeniedException extends Exception {

    private final int force;

    public PaperOwnershipDeniedException(int force) {
        super("Paper ownership denied");
        this.force = force;
    }

    public int getForce() {
        return force;
    }
}
