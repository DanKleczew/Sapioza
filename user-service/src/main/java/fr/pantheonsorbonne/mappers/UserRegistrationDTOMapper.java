package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.enums.Roles;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class UserRegistrationDTOMapper implements EntityDTOMapper< UserRegistrationDTO, UserDTO> {

    @Override
    public UserRegistrationDTO mapEntityToDTO(UserDTO entity) {
        return null;
    }

    @Override
    public UserDTO mapDTOToEntity(UserRegistrationDTO dto) {
        return new UserDTO(
                null,
                dto.name(),
                dto.firstName(),
                dto.email(),
                dto.password(),
                null,
                LocalDate.now(),
                Roles.USER,
                null
        );
    }
}
