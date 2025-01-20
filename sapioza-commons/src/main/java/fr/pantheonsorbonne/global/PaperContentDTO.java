package fr.pantheonsorbonne.global;

import java.io.Serializable;

public record PaperContentDTO(
        String paperUuid,
        byte[] pdf
) implements Serializable {
    // Transaction DTO between the paper-service and the storage-service
}
