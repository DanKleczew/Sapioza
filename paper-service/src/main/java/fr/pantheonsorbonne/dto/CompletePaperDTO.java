package fr.pantheonsorbonne.dto;

public record CompletePaperDTO(
        PaperDTO metaData,
        String body
) {
    // As posted by the user
}
