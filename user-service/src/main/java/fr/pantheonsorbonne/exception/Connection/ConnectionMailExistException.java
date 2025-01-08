package fr.pantheonsorbonne.exception.Connection;

public class ConnectionMailExistException extends ConnectionException {
    public ConnectionMailExistException(String mail) {
        super("User with mail :" + mail + " already exist");
    }


}
