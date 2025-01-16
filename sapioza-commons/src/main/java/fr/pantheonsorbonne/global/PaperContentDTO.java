package fr.pantheonsorbonne.global;

public record PaperContentDTO(
        Long paperId,
        String paperUuid,
        String body
) {
    // Transaction DTO between the paper-service and the storage-service
}
