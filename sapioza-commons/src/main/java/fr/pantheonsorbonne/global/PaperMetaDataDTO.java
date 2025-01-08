package fr.pantheonsorbonne.global;

import fr.pantheonsorbonne.enums.ResearchField;

import java.util.Date;
import java.util.List;

public record PaperMetaDataDTO(
        Long PaperId,
        String title,
        Long authorId,
        Date publicationDate
){
    // Transaction DTO between the paper-service and the notification-service
}

