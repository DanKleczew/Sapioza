package fr.pantheonsorbonne.dto.PaperDTOs;

public record AlteredPaperDTO(
        Long paperId,
        String body,
        Long authorId) {
    // Updated paper body for pdf generation
}
