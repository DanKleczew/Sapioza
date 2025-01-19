package fr.pantheonsorbonne.global;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public record PaperMetaDataDTO(
        Long PaperId,
        String title,
        Long authorId,
        Date publicationDate
) implements Serializable {
    // Transaction DTO between the paper-service and the notification-service
}

