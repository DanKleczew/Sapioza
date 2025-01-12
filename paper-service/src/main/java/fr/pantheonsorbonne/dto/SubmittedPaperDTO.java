package fr.pantheonsorbonne.dto;

public record SubmittedPaperDTO(
        PaperDTO metaData,
        String body
) {
    // As posted by the user
}
