package fr.pantheonsorbonne.dto.PaperDTOs;

import fr.pantheonsorbonne.enums.ResearchField;

import java.util.Date;
import java.util.List;

public record PaperDTO(
        String title,
        Long authorId,
        ResearchField field,
        String publishedIn,
        Date publicationDate,
        String keywords,
        String abstract_,
        String DOI,
        Integer likes,
        Integer dislikes,
        List<String> comments
        ) {
        // Mirror DTO of the Paper entity
}



