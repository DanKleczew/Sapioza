package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.NotificationGateway;
import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.dao.PaperCreationDAO;
import fr.pantheonsorbonne.dto.SubmittedPaperDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotCreatedException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.mappers.PaperMapper;
import fr.pantheonsorbonne.model.Paper;
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

    public PaperMetaDataDTO createPaper(SubmittedPaperDTO submittedPaperDTO) throws PaperNotCreatedException,
            PaperDatabaseAccessException {
        Paper paper = paperMapper.mapDTOToEntity(submittedPaperDTO.metaData());

        try {
            paper = paperCreationDAO.createPaper(paper);
        } catch (PaperDatabaseAccessException e) {
            throw new PaperNotCreatedException();
        }

        PaperMetaDataDTO paperMetaDataDTO = paperMapper.mapPaperToPaperMetaDataDTO(paper);
        try {
            // Send Paper Metadata to notification-service
            this.notificationGateway.newPaper(paperMetaDataDTO);

            PaperContentDTO paperContentDTO =
                    new PaperContentDTO(paper.getId(), paper.getUuid(), submittedPaperDTO.body());
            // Send Paper Content to storage-service
            this.storageGateway.newPaper(paperContentDTO);
        } catch (InternalCommunicationException e) {
            // Rollback
            try {
                paperDeletionService.deletePaper(paper.getId());
            } catch (PaperNotFoundException ex) {
                Log.warn("Paper not found while attempting to delete: " + paper.getId());
            }
            throw new PaperNotCreatedException();
        }
        return paperMetaDataDTO;
    }
}
