package fr.pantheonsorbonne.global;

import java.util.List;

public record UserFollowsDTO(
        Long userId,
        List<Long> followsId
) {
}
