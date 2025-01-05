package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.enums.ResearchField;

public record Filter(String title, Long authorId, String abstract_, String keywords, String revue, ResearchField field) {
}
