package fr.pantheonsorbonne.dto;

public record OpinionDTO (
    Long paperId,
    int opinion,
    Long readerId

    // Mirror DTO of Review entity
) {}
