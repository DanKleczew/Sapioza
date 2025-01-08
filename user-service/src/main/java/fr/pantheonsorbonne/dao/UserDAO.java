package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.entity.User;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;


@ApplicationScoped
public class UserDAO {

    @PersistenceContext
    EntityManager em;


    public User getUserById(Long id) {
        User user = null;
        try {
            user = em.find(User.class, id);
        } catch (RuntimeException re) {
            Log.error("getUser failed", re);
        }
        return user;
    }



    public void createTestUser() {
        User user = new User();
        try {
            user.setName("test");
            user.setFirstName("test");
            user.setEmail("test");
            user.setPassword("test");
            em.persist(user);
        } catch (RuntimeException re) {
            Log.error("createTestUser failed", re);
        }
    }

    public void updateUser(User user) {
        try {
            em.merge(user);
        } catch (RuntimeException re) {
            Log.error("updateUser failed", re);
        }
    }

    public void updateUserName(long id, String name) {
        try {
            User user = this.getUserById(id);
            user.setName(name);
            em.merge(user);
        } catch (RuntimeException re) {
            Log.error("updateUserName failed", re);
        }
    }

    public void updateUserFirstName(long id, String firstName) {
        try {
            User user = this.getUserById(id);
            user.setFirstName(firstName);
            em.merge(user);
        } catch (RuntimeException re) {
            Log.error("updateUserFirstName failed", re);
        }
    }

    public List<User> addMultipleSubscribers(long id, List<User> users) throws UserException {
        User user = this.getUserById(id);
        for (User u : users) {
            if(u.getId() == id) {
                throw new UserException("User can't subscribe to himself");
            }
            if(this.getUserById(u.getId()) == null) {
                throw new UserNotFoundException(u.getId());
            }
            user.addFollower(u);
        }
        em.merge(user);
        return user.getUsers();
    }

    public List<User> addMultipleSubscribersById(long id, List<Long> followersId) throws UserException {
        User user = this.getUserById(id);
        for (Long followerId : followersId) {
            if(Objects.equals(user.getId(), followerId)) {
                throw new UserException("User can't subscribe to himself");
            }
            User follower = this.getUserById(followerId);
            if(follower == null) {
                throw new UserNotFoundException(followerId);
            }
            user.addFollower(follower);
        }
        em.merge(user);
        return user.getUsers();
    }

    public UserDTO deleteUserById(Long id){
        User user = em.find(User.class, id);
        return this.deleteUser(user);
    }

    public UserDTO deleteUser(User user){
        user.setDeletionDate();
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getName(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsersIds()
        );
        em.merge(user);
        return userDTO;
    }

    //will be deleted
    public void deleteUserByMail(String email){
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        this.deleteUser(user);
    }

}