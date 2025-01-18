package fr.pantheonsorbonne.global;

import java.io.Serializable;

public record UserInfoDTO(
        Long id,
        String firstName,
        String lastName,
        String email
) implements Serializable {
}
