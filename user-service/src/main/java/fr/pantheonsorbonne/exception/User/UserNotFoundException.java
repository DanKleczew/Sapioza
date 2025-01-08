package fr.pantheonsorbonne.exception.User;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(Long id) {
        super("User with id :" + id + " not found");
    }


}
