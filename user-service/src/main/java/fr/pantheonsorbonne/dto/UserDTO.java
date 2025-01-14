package fr.pantheonsorbonne.dto;

import java.util.List;

public record UserDTO(Long id,
                      String name,
                      String firstName,
                      String email,
                      String password,
                      List<Long> UsersIds) {

    @Override
    public String toString(){
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", UsersIds=" + UsersIds.toString() +
                '}';
    }
}

