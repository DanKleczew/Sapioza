package fr.pantheonsorbonne.exception.User;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String userEmail) {
        super("A user with the email " + userEmail + " already exists");
    }
}
