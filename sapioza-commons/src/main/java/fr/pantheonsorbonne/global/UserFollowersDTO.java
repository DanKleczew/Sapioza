package fr.pantheonsorbonne.global;

import java.io.Serializable;
import java.util.List;

public record UserFollowersDTO(
        Long userId,
        List<UserInfoDTO> followers
) implements Serializable {

}
