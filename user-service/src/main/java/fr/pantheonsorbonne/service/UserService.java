package fr.pantheonsorbonne.service;


import fr.pantheonsorbonne.camel.gateway.UserAccount;
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
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.model.User;
import fr.pantheonsorbonne.service.interfaces.UserServiceInterface;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class UserService implements UserServiceInterface {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    @Transactional
    public UserDTO getUser(Long id) {
        User user = userDAO.getUser(id);
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
        return userDAO.getUser(id).getUsers();
    }

    public List<User> getSubscribers(String email) {
        return userDAO.getUser(email).getUsers();
    }

    public List<Long> getSubscribersId(Long id){
        return userDAO.getUser(id).getUsersIds();
    }

    public List<Long> getSubscribersId(String email){
        return userDAO.getUser(email).getUsersIds();
    }

    @Transactional
    public void createUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException {
        if(this.getUser(userRegistrationDTO.email()) != null) {
            throw new UserAlreadyExistsException(userRegistrationDTO.email());
        }
        UserDTO userDTO = userMapper.mapRegistrationToUserDTO(userRegistrationDTO);
        User user = userMapper.mapDTOToEntity(userDTO);
        userDAO.addDefaultValuesForUser(user);
        userDAO.updateUser(user);
    }

    @Transactional
    public Boolean deleteUser(Long id, String password) throws UserException {
        UserDTO userDTO = this.getUser(id);
        if (userDTO == null) {
            return false;
        }
        if (!userDTO.password().equals(password)) {
            return false;
        }
        userDAO.deleteUser(id);
        return true;
    }

    @Transactional
    public void deleteUser(UserDeletionDTO userDeletionDTO) throws UserNotFoundException, UserAuthenticationException {
        UserDTO userDTO = this.getUser(userDeletionDTO.id());
        if (userDTO == null) {
            throw new UserNotFoundException(userDeletionDTO.id());
        }
        if (!userDTO.password().equals(userDeletionDTO.password())) {
            throw new UserAuthenticationException(userDeletionDTO.id());
        }
        userDAO.deleteUser(userDTO.id());
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

    public List<Long> listOfUserDTOToListOfLong(List<UserDTO> userDTOList) {
        List<Long> userIdList = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            userIdList.add(userDTO.id());
        }
        return userIdList;
    }

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

    public void responseUserInformation(Long id){
        try {
            UserInfoDTO userInfoDTO = this.getUserInformation(id);
            this.userAccount.responseUserInformation(userInfoDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while responding user information");
        }
    }

    public UserDTO getUserByUuid(String uuid) throws UserNotFoundException {
        UserDTO userDTO = this.userDAO.getUserByUuid(uuid);
        if(userDTO == null) {
            throw new UserNotFoundException(uuid);
        }
        return userDTO;
    }

    public String getUserUuid(Long id) {
        return this.getUser(id).uuid();
    }

    public Boolean identificationByUuidAndId(String uuid, Long id) throws UserNotFoundException {
        Log.error("identificationByUuidAndId : " + uuid + " " + id);
        UserDTO userDTO = this.getUserByUuid(uuid);
        Log.error("identificationByUuidAndId : " + userDTO);
        return userDTO.id().equals(id);
    }

    public String getUuid(String email, String password) throws ConnectionException {
        UserDTO userDTO = this.connectionUser(email, password);
        return userDTO.uuid();
    }


    public void initService() {

        for (int i = 1; i <= 100; i++) {
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(
                    "Dan" + i,
                    "va" + i,
                    "te@faire" + i,
                    "foutre" + i
            );
            this.createUser(userRegistrationDTO);
        }

        for (int i = 2; i <= 20; i++) {
            this.subscribTo((long) 99, (long) i);
        }

        for (int i = 2; i <= 10; i++) {
            this.subscribTo((long) i, (long) 99);
        }
    }
}
