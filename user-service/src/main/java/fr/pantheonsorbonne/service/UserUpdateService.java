package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserFirstNameUpdate;
import fr.pantheonsorbonne.dto.UserNameUpdate;
import fr.pantheonsorbonne.dto.UserPasswordUpdate;
import fr.pantheonsorbonne.exception.User.UserAuthenticationException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserUpdateService {

    @Inject
    UserService userService;

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    public void updateName(Long id, String name) {
        User user = userDAO.getUser(id);
        user.setName(name);
        userDAO.updateUser(user);
    }

    public void updateName(UserNameUpdate userNameUpdate) throws UserNotFoundException, UserAuthenticationException {
        UserDTO userDTO = userService.getUser(userNameUpdate.id());
        userService.checkConnection(userDTO, userNameUpdate.password(), userNameUpdate.id());
        this.updateName(userNameUpdate.id(), userNameUpdate.name());
    }

    public void updateFirstName(Long id, String firstName) throws UserNotFoundException {
        User user = userDAO.getUser(id);
        user.setFirstName(firstName);
        userDAO.updateUser(user);
    }

    public void updateFirstName(UserFirstNameUpdate userFirstNameUpdate) throws UserNotFoundException, UserAuthenticationException {
        UserDTO userDTO = userService.getUser(userFirstNameUpdate.id());
        userService.checkConnection(userDTO, userFirstNameUpdate.password(), userFirstNameUpdate.id());
        this.updateFirstName(userFirstNameUpdate.id(), userFirstNameUpdate.firstName());
    }

    public void updatePassword(Long id, String lastPassword, String password) throws UserNotFoundException, UserAuthenticationException {
        User user = userDAO.getUser(id);
        UserDTO userDTO = userService.getUser(id);
        userService.checkConnection(userDTO, lastPassword, id);
        user.setPassword(password);
        userDAO.updateUser(user);
    }

    public void updatePassword(UserPasswordUpdate userPasswordUpdate) throws UserNotFoundException, UserAuthenticationException {
        UserDTO userDTO = userService.getUser(userPasswordUpdate.id());
        userService.checkConnection(userDTO, userPasswordUpdate.password(), userPasswordUpdate.id());
        this.updatePassword(userPasswordUpdate.id(), userPasswordUpdate.password(), userPasswordUpdate.newPassword());
    }
}
