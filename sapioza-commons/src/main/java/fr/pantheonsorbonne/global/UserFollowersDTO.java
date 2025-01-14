package fr.pantheonsorbonne.global;

import java.util.List;

public record UserFollowersDTO(
        Long userId,
        List<Long> followersId
) {

}
