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

    public void deleteUser(Long id, String password) throws UserNotFoundException, UserAuthenticationException /* throws UserException */  {
        UserDTO userDTO = this.userService.getUser(id);
        this.userService.checkConnection(userDTO, password, id);
        this.userService.userDAO.deleteUser(id);
    }

    public void deleteUser(String mail, String password) throws UserNotFoundException, UserAuthenticationException /* throws UserException */  {
        UserDTO userDTO = this.userService.getUser(mail);
        this.deleteUser(userDTO.id(), password);
    }

    public void deleteUser(UserDeletionDTO userDeletionDTO) throws UserNotFoundException, UserAuthenticationException {
        this.deleteUser(userDeletionDTO.id(), userDeletionDTO.password());
    }
}
