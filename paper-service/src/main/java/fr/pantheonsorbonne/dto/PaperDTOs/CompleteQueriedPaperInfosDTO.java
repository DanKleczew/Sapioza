package fr.pantheonsorbonne.dto.PaperDTOs;

public record CompleteQueriedPaperInfosDTO(
        Long paperId,
        QueriedPaperInfosDTO queriedPaperInfosDTO
) {
}
