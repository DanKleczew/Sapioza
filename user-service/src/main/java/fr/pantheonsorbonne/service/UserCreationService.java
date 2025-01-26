package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.User.UserAlreadyExistsException;
import fr.pantheonsorbonne.mappers.UserRegistrationDTOMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserCreationService{

    @Inject
    UserService userService;

    @Inject
    UserDAO userDAO;

    @Inject
    UserRegistrationDTOMapper userRegistrationDTOMapper;

    public void createUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException {

        if(this.userService.getUser(userRegistrationDTO.email()) != null) {
            throw new UserAlreadyExistsException(userRegistrationDTO.email());
        }
        UserDTO userDTO = this.userRegistrationDTOMapper.mapDTOToEntity(userRegistrationDTO);
        this.userDAO.updateUser(userDTO);
    }

    public void initService() throws UserAlreadyExistsException {

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
            this.userService.subscribeTo((long) 99, (long) i);
        }

        for (int i = 2; i <= 10; i++) {
            this.userService.subscribeTo((long) i, (long) 99);
        }
    }
}
