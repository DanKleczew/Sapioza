package fr.pantheonsorbonne.service;


import fr.pantheonsorbonne.camel.gateway.UserAccount;
import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.Connection.ConnectionException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.model.User;
import fr.pantheonsorbonne.service.interfaces.UserServiceInterface;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class UserService implements UserServiceInterface {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

     @Inject
    UserAccount userAccount;

    @Transactional
    public UserDTO getUser(Long id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            System.out.println("User not found");
            Log.debug("User not found");
            return null;
        }
        userDAO.getUsersListId(id);
        System.out.println(user.toString());
        return userMapper.mapEntityToDTO(user);
    }

    public UserDTO getUser(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        return userMapper.mapEntityToDTO(user);
    }

    @Transactional
    public void subscribTo(Long idUser1, Long idUser2) {
        User user = userDAO.getUserById(idUser1);
        User user2 = userDAO.getUserById(idUser2);
        if(this.isSubscribed(user, user2)){
            return;
        }
        user.subscribeTo(user2);
        userDAO.updateUser(user);
    }

    public Boolean isSubscribed(User user1, User user2) {
        if(user1 == null || user2 == null) {
            return false;
        }
        List<Long> user1Followers = user1.getUsersIds();
        return user1Followers.contains(user2.getId());
    }

    @Transactional
    public List<User> getSubscribers(Long id) {
        return userDAO.getUserById(id).getUsers();
    }

    public List<User> getSubscribers(String email) {
        return userDAO.getUserByEmail(email).getUsers();
    }

    public List<Long> getSubscribersId(Long id){
        return userDAO.getUserById(id).getUsersIds();
    }

    public List<Long> getSubscribersId(String email){
        return userDAO.getUserByEmail(email).getUsersIds();
    }

    @Transactional
    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = userMapper.mapRegistrationToUserDTO(userRegistrationDTO);
        User user = userMapper.mapDTOToEntity(userDTO);
        userDAO.addDefaultValuesForUser(user);
        userDAO.updateUser(user);
    }

    @Transactional
    public Boolean deleteUser(Long id, String password) {
        UserDTO userDTO = this.getUser(id);
        if (userDTO == null) {
            return false;
        }
        if (!userDTO.password().equals(password)) {
            return false;
        }
        userDAO.deleteUserById(id);
        return true;
    }

    @Transactional
    public Boolean deleteUser(String email, String password) {
        UserDTO userDTO = this.getUser(email);
        if (userDTO == null) {
            return false;
        }
        if (!userDTO.password().equals(password)) {
            return false;
        }
        userDAO.deleteUserById(userDTO.id());
        return true;
    }

    @Transactional
    public void createTestUser() {
        userDAO.createTestUser();
    }

    public UserDTO connectionUser(String email, String password) throws ConnectionException {
        User user = userDAO.connection(email, password);
        UserDTO userDTO = userMapper.mapEntityToDTO(user);
        if (userDTO == null) {
            throw new ConnectionException(email, new Throwable());
        }
        return userDTO;
    }

    public List<Long> findUserFollowersID(Long id) {
        List<User> users = this.findUserFollows(id);
        List<Long> followersList = new ArrayList<>();
        for (User user : users) {
            followersList.add(user.getId());
            System.out.println(user.toString() + " is a follow " + id );
        }
        return followersList;
    }

    public List<User> findUserFollows(Long id){
        return userDAO.findUserFollows(id);
    }

    public List<Long> findUserFollowsID(Long id) {
        List<User> users = this.findUserFollows(id);
        for (User user : users) {
            Log.debug(user.toString() + " is a follow " + id );
            System.out.println(user.toString() + " is a follow " + id );
        }
        return this.transformUserListToIdList(users);
    }

    public List<Long> transformUserListToIdList(List<User> users) {
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    public List<UserDTO> findUserFollowersDTO(Long id){
        List<User> users = this.findUserFollows(id);
        List<UserDTO> followersList = new ArrayList<>();
        for (User user : users) {
            followersList.add(userMapper.mapEntityToDTO(user));
            System.out.println(userMapper.mapEntityToDTO(user) + " is a follow " + id );
        }
        //System.out.println(followersList);
        return followersList;
    }

    public UserInfoDTO getUserInfo(Long id) {
        try {
            User user = this.userDAO.getUserById(id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            UserInfoDTO userInfoDTO = new UserInfoDTO(
                    user.getId(),
                    user.getName(),
                    user.getFirstName(),
                    user.getEmail());
            this.userAccount.responseUserInformation(userInfoDTO);
            return userInfoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserFollowersDTO getUserFollowers(Long id) {
        try {
            List<UserDTO> followers = this.findUserFollowersDTO(id);
            UserFollowersDTO userFollowersDTO = new UserFollowersDTO(id, this.listOfUserDTOToListOfLong(followers));
            this.userAccount.getFollowers(userFollowersDTO);
            return userFollowersDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Long> listOfUserDTOToListOfLong(List<UserDTO> userDTOList) {
        List<Long> userIdList = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            userIdList.add(userDTO.id());
        }
        return userIdList;
    }

    public UserFollowsDTO getUserFollows(Long id) {
        try {
            User user = this.userDAO.getUserById(id);
            List<User> follows = user.getUsers();
            List<UserDTO> followsDTO = new ArrayList<>();
            for (User follow : follows) {
                followsDTO.add(this.userMapper.mapEntityToDTO(follow));
            }
            UserFollowsDTO userFollowsDTO = new UserFollowsDTO(id, this.listOfUserDTOToListOfLong(followsDTO));
            this.userAccount.getFollows(userFollowsDTO);
            return userFollowsDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public UserInfoDTO getUserInformation(Long id){
        try {
            User user = this.userDAO.getUserById(id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            UserInfoDTO userInfoDTO = new UserInfoDTO(
                    user.getId(),
                    user.getName(),
                    user.getFirstName(),
                    user.getEmail());
            return userInfoDTO;
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void responseUserInformation(Long id){
        try {
            UserInfoDTO userInfoDTO = this.getUserInformation(id);
            this.userAccount.responseUserInformation(userInfoDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while responding user information");
        }
    }

    public UserDTO getUserByUuid(String uuid) throws UserNotFoundException {
        User user = this.userDAO.getUserByUuid(uuid);
        UserDTO userDTO = this.userMapper.mapEntityToDTO(user);
        if(userDTO == null) {
            throw new UserNotFoundException(uuid);
        }
        return userDTO;
    }

    public String getUserUuid(Long id) {
        return this.getUser(id).uuid();
    }







}
