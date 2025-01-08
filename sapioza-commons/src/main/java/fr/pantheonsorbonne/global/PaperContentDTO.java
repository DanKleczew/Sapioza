package fr.pantheonsorbonne.global;

public record PaperContentDTO(
        Long PaperId,
        String body
) {
    // Transaction DTO between the paper-service and the storage-service
}
