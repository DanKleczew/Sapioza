package fr.pantheonsorbonne.dto.PaperDTOs;

public record SubmittedPaperDTO(
        PaperDTO metaData,
        String body
) {
    // As posted by the user
}
