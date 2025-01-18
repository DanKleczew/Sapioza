package fr.pantheonsorbonne.global;

import java.io.Serializable;
import java.util.List;

public record UserFollowsDTO(
        Long userId,
        List<Long> followsId
) implements Serializable {
}
