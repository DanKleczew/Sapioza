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
import jakarta.persistence.NoResultException;
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
        user = em.find(User.class, id);
        return user;
    }

    public void updateUser(User user) {
        em.merge(user);
    }

    public void updateUser(UserDTO userDTO){
        User user = this.userMapper.mapDTOToEntity(userDTO);
        this.updateUser(user);
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
            throw new RuntimeException(re.getMessage());
        }
    }

    public List<User> addMultipleSubscribers(Long id, List<User> users) throws UserException {
        User user = this.getUser(id);
        for (User u : users) {
            if(u.getId().equals(id)) {
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

    public List<User> addMultipleSubscribersById(Long id, List<Long> followersId) throws UserException {
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

    public void deleteUser(Long id) throws UserNotFoundException {
        // Step 5 : Find the user
        User user = em.find(User.class, id);
        if (user == null) {
            // Step 5.5 : Stupid because it's already checked in the previous method
            throw new UserNotFoundException(id);
        }
        // Step 6 : Call ANOTHER method to delete the user
        this.deleteUser(user);
    }


    public void deleteUser(User user){
        try {
            // Step 7 Ok, so we don't actually delete the user, we just set a deletion date
            //user.setDeletionDate();
            // Step 8 : Call the DAO method to UPDATE ??? the user
            // this.updateUser(user);
            // New steps : Remove the user from the followers table and then delete the user
            this.em.createNativeQuery("DELETE FROM User_User WHERE User_id = :userId")
                            .setParameter("userId", user.getId())
                    .executeUpdate();
            this.em.createNativeQuery("DELETE FROM User_User WHERE Follower_id = :userId")
                    .setParameter("userId", user.getId())
                    .executeUpdate();
            this.em.remove(user);
        } catch (RuntimeException re) {
            Log.error("deleteUser failed", re);
            throw new RuntimeException(re.getMessage());
        }
    }

    //will be deleted WHEN WILL IT ??? THE PROJECT IS DUE 2 WEEKS AGO
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
            throw new RuntimeException(re.getMessage());
        }
        return user;
    }

    public User connection(String email, String password) throws UserException {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (user.getPassword().equals(password)) {
                return user;
            }
            throw new UserException("Wrong password");
        } catch (NoResultException e) {
            throw new UserNotFoundException(email);
        }
        catch (RuntimeException re) {
            throw new RuntimeException(re.getMessage());
        }
    }


    public User getUser(String email) {
        User user = null;
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (RuntimeException e) {
            return null;
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

    /*
    public void addDefaultValuesForUser(User user){
        if(user.getCreationDate() == null){
            user.setCreationDate(LocalDate.now());
        }
        if(user.getRole() == null){
            user.setRole(Roles.USER);
        }
    }
     */
    /*
    public List<User> findUserFollowersByMail(String email) {
        return  em.createQuery("SELECT uu FROM User u JOIN User_User uu JOIN uu.Users uuu WHERE  uuu.email = :email", User.class)
                .setParameter("email", email) // Correctly bind the parameter
                .getResultList();
    }

     */
}