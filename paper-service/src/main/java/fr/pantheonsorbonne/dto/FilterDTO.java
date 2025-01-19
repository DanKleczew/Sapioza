package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.enums.ResearchField;

public record FilterDTO(String title,
                        Long authorId,
                        String abstract_,
                        String keywords,
                        String revue,
                        ResearchField field,
                        Boolean AscDate,
                        Boolean DescDate,
                        String DOI) {
    // Filter for paper search
}
