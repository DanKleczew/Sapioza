package fr.pantheonsorbonne.dto.PaperDTOs;

import fr.pantheonsorbonne.global.UserIdentificationDTO;

public record DeletionPaperDTO(Long articleId, UserIdentificationDTO userIdentificationDTO) {
}
