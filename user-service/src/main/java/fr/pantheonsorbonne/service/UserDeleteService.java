package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserDeletionDTO;
import fr.pantheonsorbonne.exception.User.UserAuthenticationException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserDeleteService {

    @Inject
    UserService userService;

    @Inject
    UserDAO userDAO;

    public void deleteUser(Long id, String password) throws UserNotFoundException, UserAuthenticationException /* throws UserException */  {
        // Step 2 : Calls getUser to get a DTO; Maybe throws UserNotFoundException
        UserDTO userDTO = this.userService.getUser(id);
        // Step 3 : Calls checkConnection to check the password; Maybe throws UserAuthenticationException
        this.userService.checkConnection(userDTO, password, id);
        // Step 4 : Calls the DAO method to delete WHICH DAO ?? WTF
        // this.userService.userDAO.deleteUser(id);
        // Step 4.2 : Calls the RIGHT DAO method to delete
        this.userDAO.deleteUser(id);
    }

    public void deleteUser(String mail, String password) throws UserNotFoundException, UserAuthenticationException /* throws UserException */  {
        UserDTO userDTO = this.userService.getUser(mail);
        this.deleteUser(userDTO.id(), password);
    }

    public void deleteUser(UserDeletionDTO userDeletionDTO) throws UserNotFoundException, UserAuthenticationException {
        // Step 1 : Calls the first method
        this.deleteUser(userDeletionDTO.id(), userDeletionDTO.password());
    }
}
