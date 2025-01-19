package fr.pantheonsorbonne.global;

import java.io.Serializable;

public record UserIdentificationDTO(Long userId, String userUUID) implements Serializable {
    // User identification for paper deletion
}
