package fr.pantheonsorbonne.exception.User;

public class UserAuthenticationException extends UserException {
    public UserAuthenticationException(Long id) {
        super("User authentication failed for user: " + id);
    }
    public UserAuthenticationException(String email) {
        super("User authentication failed for mail: " + email);
    }
}
