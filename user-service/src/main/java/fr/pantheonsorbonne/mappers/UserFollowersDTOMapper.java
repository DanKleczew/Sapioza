package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserFollowersDTOMapper implements EntityDTOMapper<UserFollowersDTO, UserDTO> {

    @Inject
    UserService userService;

    @Override
    public UserFollowersDTO mapEntityToDTO(UserDTO entity) {
        List<UserInfoDTO> userFollowers = userService.getSubscribersDTO(entity.id());
        return new UserFollowersDTO(
                entity.id(),
                userFollowers
        );
    }

    @Override
    public UserDTO mapDTOToEntity(UserFollowersDTO dto) {
        return null;
    }
}
