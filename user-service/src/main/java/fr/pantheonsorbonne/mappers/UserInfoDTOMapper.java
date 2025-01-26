package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserInfoDTOMapper implements EntityDTOMapper<UserInfoDTO, UserDTO > {

    @Override
    public UserInfoDTO mapEntityToDTO(UserDTO entity) {
        return new UserInfoDTO(
                entity.id(),
                entity.firstName(),
                entity.name(),
                entity.email()
        );
    }

    @Override
    public UserDTO mapDTOToEntity(UserInfoDTO dto) {
        return null;
    }
}
