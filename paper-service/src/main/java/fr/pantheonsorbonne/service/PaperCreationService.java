package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.NotificationGateway;
import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.camel.gateway.UserGateway;
import fr.pantheonsorbonne.dao.PaperCreationDAO;
import fr.pantheonsorbonne.dto.PaperDTOs.CompletePaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotCreatedException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.mappers.PaperMapper;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.pdf.PdfGenerator;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaperCreationService {

    @Inject
    PaperCreationDAO paperCreationDAO;

    @Inject
    PaperMapper paperMapper;

    @Inject
    NotificationGateway notificationGateway;

    @Inject
    StorageGateway storageGateway;

    @Inject
    PaperDeletionService paperDeletionService;

    @Inject
    PdfGenerationService pdfGenerationService;

    public PaperMetaDataDTO createPaper(SubmittedPaperDTO submittedPaperDTO) throws PaperNotCreatedException {
        // Transform SubmittedPaperDTO to Paper
        Paper paper = paperMapper.mapDTOToEntity(submittedPaperDTO.metaData());
        // Persist Paper
        paper = this.persistPaper(paper);
        try {
            // Map to PaperMetaDataDTO
            PaperMetaDataDTO paperMetaDataDTO = paperMapper.mapPaperToPaperMetaDataDTO(paper);
            // Send Paper Metadata to notification-service
            this.notificationGateway.newPaper(paperMetaDataDTO);

            // Generate PDF
            byte[] pdf = pdfGenerationService.generatePdf(submittedPaperDTO);

            // Send generated pdf to storage-service
            this.storageGateway.newPaper(new PaperContentDTO(paper.getUuid(), pdf));

            // Return PaperMetaDataDTO (with ID for information through the API along HTTP code 200)
            return paperMetaDataDTO;

        } catch (InternalCommunicationException e) {
            // Rollback if an error occurs while sending the pdf
            try {
                paperDeletionService.deletePaperNoCheck(paper.getId());
            } catch (PaperNotFoundException ex) {
                Log.warn("Paper not found while attempting to delete: " + paper.getId());
            }
            throw new PaperNotCreatedException();
        }
    }

    private Paper persistPaper(Paper paper) throws PaperNotCreatedException {
        try {
            return paperCreationDAO.createPaper(paper);
        } catch (PaperDatabaseAccessException e) {
            throw new PaperNotCreatedException();
        }
    }


}
