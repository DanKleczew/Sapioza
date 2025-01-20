package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.UserGateway;
import fr.pantheonsorbonne.dto.PaperDTOs.CompletePaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.pdf.PdfGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PdfGenerationService {

    @Inject
    UserGateway userGateway;

    @Inject
    PdfGenerator pdfGenerator;

    public byte[] generatePdf(SubmittedPaperDTO submittedPaperDTO) {
        UserInfoDTO userInfoDTO = this.userGateway.getUserInfos(submittedPaperDTO.metaData().authorId());

        CompletePaperDTO completePaperDTO = new CompletePaperDTO(submittedPaperDTO.metaData(),
                userInfoDTO, submittedPaperDTO.body());
        return this.pdfGenerator.generatePdf(completePaperDTO);
    }
}
