package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;

import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper implements EntityDTOMapper<UserDTO, User> {

    @Override
    public UserDTO mapEntityToDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getFirstName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getUsersIds());
    }

    @Override
    public User mapDTOToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.id());
        user.setName(dto.name());
        user.setFirstName(dto.firstName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        // A new user does not have any followers
        user.setUsers(null);
        return user;
    }

    public UserDTO mapRegistrationToUserDTO(UserRegistrationDTO dto) {
        return new UserDTO(
                null,
                dto.name(),
                dto.firstName(),
                dto.email(),
                dto.password(),
                null);
    }
}
