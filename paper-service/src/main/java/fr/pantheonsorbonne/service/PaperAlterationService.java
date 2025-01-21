package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.camel.gateway.UserGateway;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.PaperDTOs.AlteredPaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.PaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import fr.pantheonsorbonne.enums.Cause;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperOwnershipDeniedException;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.mappers.PaperMapper;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.pdf.PdfGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaperAlterationService {

    @Inject
    PaperQueryDAO paperQueryDAO;

    @Inject
    PdfGenerationService pdfGenerationService;

    @Inject
    StorageGateway storageGateway;

    @Inject
    PaperMapper paperMapper;

    public void alterPaper(AlteredPaperDTO alteredPaperDTO) throws PaperNotFoundException,
                                                                   PaperOwnershipDeniedException {
        Paper paper = this.paperQueryDAO.getPaper(alteredPaperDTO.paperId());
        if (paper == null) {
            throw new PaperNotFoundException(alteredPaperDTO.paperId());
        }
        if (paper.getAuthorId() != alteredPaperDTO.authorId()) {
            throw new PaperOwnershipDeniedException(Cause.FORBIDDEN);
        }
        PaperDTO paperDTO = paperMapper.mapEntityToDTO(paper);
        SubmittedPaperDTO submittedPaperDTO = new SubmittedPaperDTO(paperDTO, alteredPaperDTO.body());

        this.storageGateway.updatePaper(new PaperContentDTO(paper.getUuid(),
                this.pdfGenerationService.generatePdf(submittedPaperDTO)));
    }
}
