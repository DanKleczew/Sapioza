package fr.pantheonsorbonne.service;


import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.Connection.ConnectionException;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    @Transactional
    public User getUserInfos(long id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            System.out.println("User not found");
        }
        return user;
    }

    @Transactional
    public void subscribing (long idUser1, long idUser2) {
        User user = userDAO.getUserById(idUser1);
        User user2 = userDAO.getUserById(idUser2);
        user.subscribeTo(user2);
        userDAO.updateUser(user);
    }

    @Transactional
    public List<User> getSubscribers(long id) {
        return userDAO.getUserById(id).getUsers();
    }

    public List<User> getSubscribers(String email) {
        return userDAO.getUserByEmail(email).getUsers();
    }

    @Transactional
    public void createAccount(UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = userMapper.mapRegistrationToUserDTO(userRegistrationDTO);
        User user = userMapper.mapDTOToEntity(userDTO);
        userDAO.updateUser(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userDAO.deleteUserById(id);
    }

    @Transactional
    public void createTestUser() {
        userDAO.createTestUser();
    }

    public Long connectionUser(String email, String password) throws ConnectionException {
        User user = userDAO.connection(email, password);
        UserDTO userDTO = userMapper.mapEntityToDTO(user);
        if (userDTO == null) {
            throw new ConnectionException(email, new Throwable());
        }
        return userDTO.id();
    }









}
