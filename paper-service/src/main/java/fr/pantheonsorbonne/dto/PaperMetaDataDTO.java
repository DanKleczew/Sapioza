package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.enums.ResearchField;

import java.util.Date;
import java.util.List;

public record PaperMetaDataDTO(
        Long PaperId,
        String title,
        Long authorId,
        ResearchField field,
        Date publicationDate
){
    // Transaction DTO between the paper-service and the notification-service
}

