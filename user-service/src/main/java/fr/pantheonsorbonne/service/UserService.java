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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

     @Inject
    UserAccount userAccount;

    @Transactional
    public User getUserInfos(long id) {
        User user = userDAO.getUserById(id);
        userDAO.getUsersListId(id);
        System.out.println(user.toString());
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

    public List<Long> findUserFollowersID(Long id) {
        List<User> users = this.findUserFollowers(id);
        List<Long> followersList = new ArrayList<>();
        for (User user : users) {
            followersList.add(user.getId());
            System.out.println(user.toString() + " is a follow " + id );
        }
        return followersList;
    }

    public List<User> findUserFollowers(Long id){
        return userDAO.findUserFollowers(id);
    }

    public List<UserDTO> findUserFollowersDTO(Long id){
        List<User> users = this.findUserFollowers(id);
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

    public Long getUserIdByUui(String uuid) throws UserNotFoundException {
        UserDTO userDTO = this.userDAO.getUserByUuid(uuid);
        if(userDTO == null) {
            throw new UserNotFoundException(uuid);
        }
        return userDTO.id();
    }







}
