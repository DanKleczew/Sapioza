package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.enums.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record UserDTO(
                      Long id,
                      String name,
                      String firstName,
                      String email,
                      String password,
                      LocalDate creationDate,
                      Roles role,
                      String uuid
) {

    @Override
    public String toString(){
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                ", role=" + role +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}

