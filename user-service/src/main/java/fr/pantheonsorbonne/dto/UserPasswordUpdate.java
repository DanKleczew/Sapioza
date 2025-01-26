package fr.pantheonsorbonne.dto;

public record UserPasswordUpdate(
        Long id,
        String password,
        String newPassword
){

}
