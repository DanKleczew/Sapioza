package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.enums.OpinionList;

public record OpinionDTO (
    Long paperId,
    OpinionList opinion,
    Long readerId

    // Mirror DTO of Opinion entity
) {}
