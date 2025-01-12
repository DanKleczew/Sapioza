package fr.pantheonsorbonne.dto;

public record ReviewDTO(
        Long paperId,
        Long authorId,
        String comment
){
    // Mirror DTO of Review entity
}
