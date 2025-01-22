package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;

import fr.pantheonsorbonne.enums.Roles;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.model.User;
import fr.pantheonsorbonne.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserMapper implements EntityDTOMapper<UserDTO, User> {

    @Inject
    UserService userService;

    @Inject
    UserDAO userDAO;

    @Override
    public UserDTO mapEntityToDTO(User entity) /*throws UserException*/ {
        /*
        if(entity.getDeletionDate() != null) {
            throw new UserException("User is deleted");
        }
         */
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getFirstName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getUsersIds(),
                entity.getCreationDate(),
                entity.getDeletionDate(),
                entity.getRole(),
                entity.getUuid()
        );
    }

    @Override
    public User mapDTOToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.id());
        user.setName(dto.name());
        user.setFirstName(dto.firstName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setUsers(null);
        user.setCreationDate(dto.creationDate());
        user.setDeletionDate(dto.deletionDate());
        user.setRole(dto.role());
        user.setUuid(dto.uuid());
        return user;
    }
}
