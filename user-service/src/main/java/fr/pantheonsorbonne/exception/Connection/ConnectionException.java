package fr.pantheonsorbonne.exception.Connection;

public class ConnectionException extends Exception {
    private static final String connexionMessage = "Connection or register error";
    public ConnectionException(String message) {
        super( connexionMessage+ " : \n" + message);
    }

    public ConnectionException(String userMail, Throwable cause) {
        super("The mail : "+userMail+" and password do not match");
    }
}
