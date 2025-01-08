package fr.pantheonsorbonne.exception.User;

public class UserException extends Exception {
    public static final String userMessage = "User error : \n";
    public UserException(String message) {
        super(userMessage+message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Long userId) {
        super(userMessage+"User with id : "+userId+" not found");
    }


}
