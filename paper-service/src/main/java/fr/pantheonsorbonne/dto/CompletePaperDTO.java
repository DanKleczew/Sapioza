package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.global.UserInfoDTO;

public record CompletePaperDTO(PaperDTO paperDTO, UserInfoDTO userInfoDTO, String body) {
    // Complete paper for pdf generation
}
