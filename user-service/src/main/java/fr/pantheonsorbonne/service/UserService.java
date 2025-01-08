package fr.pantheonsorbonne.service;


import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

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

    @Transactional
    public void createAccount(
            String name,
            String firstName,
            String email,
            String password
    ) {
        User user = new User(name, firstName, email, password);
        userDAO.updateUser(user);
    }

    @Transactional
    public UserDTO deleteUser(Long id) {
        return userDAO.deleteUserById(id);
    }

    @Transactional
    public void createTestUser() {
        userDAO.createTestUser();
    }









}
