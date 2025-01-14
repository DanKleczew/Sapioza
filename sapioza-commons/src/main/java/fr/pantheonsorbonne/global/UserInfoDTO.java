package fr.pantheonsorbonne.global;

public record UserInfoDTO(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
