package fr.pantheonsorbonne.dto;

public record PaperBodyDTO(
        Long PaperId,
        String body
) {
    // Transaction DTO between the paper-service and the storage-service
}
