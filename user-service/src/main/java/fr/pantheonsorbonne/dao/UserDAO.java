package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.enums.Roles;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.model.User;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Transactional
@ApplicationScoped
public class UserDAO {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserMapper userMapper;


    public User getUser(Long id) {
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

    public void updateUserName(long id, String name) throws UserNotFoundException {
        try {
            User user = this.getUser(id);
            user.setName(name);
            this.updateUser(user);
        } catch (RuntimeException re) {
            Log.error("updateUserName failed", re);
            throw new UserNotFoundException(id);
        }
    }

    public void updateUserFirstName(long id, String firstName) {
        try {
            User user = this.getUser(id);
            user.setFirstName(firstName);
            this.updateUser(user);
        } catch (RuntimeException re) {
            Log.error("updateUserFirstName failed", re);
        }
    }

    public List<User> addMultipleSubscribers(long id, List<User> users) throws UserException {
        User user = this.getUser(id);
        for (User u : users) {
            if(u.getId() == id) {
                throw new UserException("User can't subscribe to himself");
            }
            if(this.getUser(u.getId()) == null) {
                throw new UserNotFoundException(u.getId());
            }
            user.addFollower(u);
        }
        this.updateUser(user);
        return user.getUsers();
    }

    public List<User> addMultipleSubscribersById(long id, List<Long> followersId) throws UserException {
        User user = this.getUser(id);
        for (Long followerId : followersId) {
            if(Objects.equals(user.getId(), followerId)) {
                throw new UserException("User can't subscribe to himself");
            }
            User follower = this.getUser(followerId);
            if(follower == null) {
                throw new UserNotFoundException(followerId);
            }
            user.addFollower(follower);
        }
        this.updateUser(user);
        return user.getUsers();
    }

    public void deleteUser(Long id) throws UserException {
        try {
            User user = em.find(User.class, id);
            this.deleteUser(user);
        }
        catch (RuntimeException re) {
            throw new UserException("failed to delete user with id " + id);
        }
    }


    public void deleteUser(User user){
        try {
            user.setDeletionDate();
            this.updateUser(user);
        } catch (RuntimeException re) {
            Log.error("deleteUser failed", re);
            throw new RuntimeException("failed to delete user with id " + user.getId());
        }
    }

    //will be deleted
    public void deleteUser(String email){
        User user = findUser(email);
        this.deleteUser(user);
    }
    public User findUser(String email) {
        User user = null;
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (RuntimeException re) {
            throw new RuntimeException("failed to find user with email " + email);
        }
        return user;
    }

    public User connection(String email, String password) {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public User getUser(String email) {
        User user = null;
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (RuntimeException re) {
            Log.error("getUserByEmail failed", re);
        }
        return user;
    }

    public List<User> getUsersListId(Long id){
        User user = this.getUser(id);
        for (User u : user.getUsers()) {
            System.out.println(u.getId());
        }
        return user.getUsers();
    }

    public List<User> findUserFollows(Long followerId) {
        //return  //em.createQuery("SELECT uu FROM User u JOIN u.Users uu JOIN uu.Users uuu WHERE  uuu.id = :id", User.class)
                //em.createNativeQuery("SELECT u.* FROM User u LEFT JOIN User_User uu ON u.id = uu.Follower_id WHERE uu.User_id = :id", User.class)
                //.setParameter(followerId, "id")
        return em.createQuery("SELECT u FROM User u JOIN u.Users uu WHERE uu.id = :id", User.class)
                //em.createQuery("SELECT u FROM User u LEFT JOIN User_User uu ON u.id = uu.User_id WHERE uu.Follower_id = :id", User.class)
                .setParameter("id", followerId) // Correctly bind the parameter
                .getResultList();
    }

    public UserDTO getUserByUuid(String uuid) {
        User user = null;
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.uuid = :uuid ", User.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (RuntimeException re) {
            Log.error("getUserByUuid failed", re);
        }
        UserDTO userDTO = userMapper.mapEntityToDTO(user);
        return userDTO;
    }

    public void addDefaultValuesForUser(User user){
        if(user.getCreationDate() == null){
            user.setCreationDate(LocalDate.now());
        }
        if(user.getRole() == null){
            user.setRole(Roles.USER);
        }
    }
    /*
    public List<User> findUserFollowersByMail(String email) {
        return  em.createQuery("SELECT uu FROM User u JOIN User_User uu JOIN uu.Users uuu WHERE  uuu.email = :email", User.class)
                .setParameter("email", email) // Correctly bind the parameter
                .getResultList();
    }

     */
}