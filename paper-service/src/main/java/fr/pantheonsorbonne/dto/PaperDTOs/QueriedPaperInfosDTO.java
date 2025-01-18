package fr.pantheonsorbonne.dto.PaperDTOs;

import fr.pantheonsorbonne.global.UserInfoDTO;

public record QueriedPaperInfosDTO(
        PaperDTO paperDTO,
        UserInfoDTO userInfoDTO) {
    // DTO for API response
}
