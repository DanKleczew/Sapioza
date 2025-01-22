package fr.pantheonsorbonne.exception.User;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(Long id) {
        super("User with id :" + id + " not found");
    }

    public UserNotFoundException(String uuid) {
        super("User with uuid :" + uuid + " not found");
    }

    public UserNotFoundException(){
        super("User not found");
    }


}
