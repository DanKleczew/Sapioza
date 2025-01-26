package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserFollowsDTOMapper implements EntityDTOMapper<UserFollowsDTO, UserDTO> {

    @Inject
    UserService userService;

    @Override
    public UserFollowsDTO mapEntityToDTO(UserDTO entity) {
        List<Long> usersIds = userService.findUserFollowsID(entity.id());
        return new UserFollowsDTO(
                entity.id(),
                usersIds
        );
    }

    @Override
    public UserDTO mapDTOToEntity(UserFollowsDTO dto) {
        return null;
    }
}
