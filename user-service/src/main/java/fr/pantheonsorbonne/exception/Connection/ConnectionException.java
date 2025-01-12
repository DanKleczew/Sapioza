package fr.pantheonsorbonne.exception.Connection;

public class ConnectionException extends Exception {
    private static final String connectionMessage = "Connection or register error";
    public ConnectionException(String message) {
        super( connectionMessage+ " : " + message);
    }

    public ConnectionException(String userMail, Throwable cause) {
        super("The mail : "+userMail+" and password do not match");
    }
}
