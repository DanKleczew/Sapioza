package fr.pantheonsorbonne.service;


import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserDeletionDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.Connection.ConnectionException;
import fr.pantheonsorbonne.exception.User.UserAlreadyExistsException;
import fr.pantheonsorbonne.exception.User.UserAuthenticationException;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.mappers.UserInfoDTOMapper;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.model.User;
import fr.pantheonsorbonne.service.interfaces.UserServiceInterface;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
@Named("UserService")
public class UserService {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    @Inject
    UserInfoDTOMapper userInfoDTOMapper;

    @Transactional
    public UserDTO getUser(Long id) throws UserNotFoundException {
        User user = userDAO.getUser(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        userDAO.getUsersListId(id);
        return userMapper.mapEntityToDTO(user);
    }

    public UserDTO getUser(String email) {
        User user = userDAO.getUser(email);
        if (user == null) {
            return null;
        }
        return userMapper.mapEntityToDTO(user);
    }

    @Transactional
    public void subscribeTo(Long idAuthor, Long idSubscriber) {
        User user = userDAO.getUser(idAuthor);
        User user2 = userDAO.getUser(idSubscriber);
        if(this.isSubscribed(user, user2)){
            return;
        }
        user.subscribeTo(user2);
        this.updateUser(user);
    }

    //Could be usefull later
    public void updateUser(UserDTO userDTO){
        this.userDAO.updateUser(userDTO);
    }

    public void updateUser(User user){
        this.userDAO.updateUser(user);
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
        return userDAO.getUser(id).getUsers();
    }

    public List<User> getSubscribers(String email) {
        return userDAO.getUser(email).getUsers();
    }

    @Transactional
    public List<Long> getSubscribersId(Long id){
        return userDAO.getUser(id).getUsersIds();
    }

    public List<Long> getSubscribersId(String email){
        return userDAO.getUser(email).getUsersIds();
    }

    @Transactional
    public List<UserInfoDTO> getSubscribersDTO(Long id){
        List<User> users = this.getSubscribers(id);
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = userMapper.mapEntityToDTO(user);
            userInfoDTOList.add(userInfoDTOMapper.mapEntityToDTO(userDTO));
        }
        return userInfoDTOList;
    }


    public void checkConnection(UserDTO userDTO, String password, Long id) throws UserNotFoundException, UserAuthenticationException {
        if (userDTO == null) {
            throw new UserNotFoundException(id);
        }
        if (!userDTO.password().equals(password)) {
            throw new UserAuthenticationException(userDTO.id());
        }
    }

    @Transactional
    public UserDTO connectionUser(String email, String password) throws UserException {
        User user = userDAO.connection(email, password);
        if(user == null){
            throw new UserNotFoundException();
        }
        UserDTO userDTO = userMapper.mapEntityToDTO(user);
        return userDTO;
    }

    public UserDTO connectionUser(Long id, String password) throws UserException {
        User user = userDAO.getUser(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return this.connectionUser(user.getEmail(), password);
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
/*
    public UserInfoDTO getUserInfo(Long id) {
        try {
            User user = this.userDAO.getUser(id);
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

 */

    public List<Long> listOfUserDTOToListOfLong(List<UserDTO> userDTOList) {
        List<Long> userIdList = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            userIdList.add(userDTO.id());
        }
        return userIdList;
    }

    /*
    public UserFollowsDTO getUserFollows(Long id) {
        try {
            User user = this.userDAO.getUser(id);
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

     */



    public UserInfoDTO getUserInformation(Long id){
        try {
            User user = this.userDAO.getUser(id);
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

    /*
    public void responseUserInformation(Long id){
        try {
            UserInfoDTO userInfoDTO = this.getUserInformation(id);
            this.userAccount.responseUserInformation(userInfoDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while responding user information");
        }
    }

     */

    public UserDTO getUserByUuid(String uuid) throws UserNotFoundException {
        UserDTO userDTO = this.userDAO.getUserByUuid(uuid);
        if(userDTO == null) {
            throw new UserNotFoundException(uuid);
        }
        return userDTO;
    }

    public String getUserUuid(Long id) throws UserNotFoundException {
        return this.getUser(id).uuid();
    }

    public Boolean identificationByUuidAndId(String uuid, Long id) throws UserNotFoundException {
        Log.error("identificationByUuidAndId : " + uuid + " " + id);
        UserDTO userDTO = this.getUserByUuid(uuid);
        Log.error("identificationByUuidAndId : " + userDTO);
        return userDTO.id().equals(id);
    }

    /*
    public String getUuid(String email, String password) throws ConnectionException {
        UserDTO userDTO = this.connectionUser(email, password);
        return userDTO.uuid();
    }

     */

}
