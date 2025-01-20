package fr.pantheonsorbonne.service.interfaces;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.model.User;

import java.util.List;

public interface UserServiceInterface {

    public UserDTO getUser(Long id);
    public UserDTO getUser(String email);



    public void createUser(UserRegistrationDTO userRegistrationDTO);

    public Boolean deleteUser(Long id, String password) throws UserException;
    public Boolean deleteUser(String email, String password) throws UserException;

    public void subscribTo(Long idUser1, Long idUser2);
    public Boolean isSubscribed(User user1, User user2);

    public List<Long> findUserFollowsID(Long id);
}
