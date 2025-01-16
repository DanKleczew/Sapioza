package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.global.UserInfoDTO;

public record QueriedPaperDTO(PaperDTO paperDTO, UserInfoDTO userInfoDTO) {
}
