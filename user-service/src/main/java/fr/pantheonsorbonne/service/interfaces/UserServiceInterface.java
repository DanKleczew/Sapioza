package fr.pantheonsorbonne.service.interfaces;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.User.UserAlreadyExistsException;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.model.User;

import java.util.List;

public interface UserServiceInterface {

    public UserDTO getUser(Long id) throws UserNotFoundException;
    public UserDTO getUser(String email);



    public void createUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException;

    public void deleteUser(Long id, String password) throws UserException;
    public void deleteUser(String email, String password) throws UserException;

    public void subscribeTo(Long idUser1, Long idUser2);
    public Boolean isSubscribed(User user1, User user2);

    public List<Long> findUserFollowsID(Long id);
}
