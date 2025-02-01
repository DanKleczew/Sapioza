package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.camel.gateway.UserGateway;
import fr.pantheonsorbonne.dao.OpinionDAO;
import fr.pantheonsorbonne.dao.PaperDeletionDAO;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dao.ReviewDAO;
import fr.pantheonsorbonne.enums.Cause;
import fr.pantheonsorbonne.global.UserIdentificationDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperOwnershipDeniedException;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaperDeletionService {

    @Inject
    PaperDeletionDAO paperDeletionDAO;

    @Inject
    PaperQueryDAO paperQueryDAO;

    @Inject
    StorageGateway storageGateway;

    @Inject
    UserGateway userGateway;

    public void deletePaper(Long id, UserIdentificationDTO userIdentificationDto)
            throws PaperNotFoundException, PaperOwnershipDeniedException {
        Paper paper = paperQueryDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        if (userIdentificationDto.userId() == null || userIdentificationDto.userUUID() == null) {
            throw new PaperOwnershipDeniedException(Cause.UNAUTHORIZED);
        }
        boolean isUserAllowed = userGateway.isUserAllowed(userIdentificationDto);
        if (paper.getAuthorId() != userIdentificationDto.userId() || !isUserAllowed ) {
            throw new PaperOwnershipDeniedException(Cause.FORBIDDEN);
        }

        paperDeletionDAO.deletePaper(paper);
        try {
            this.storageGateway.deletePaper(paper.getUuid());
        } catch (InternalCommunicationException e) {
            Log.error("Error while sending deletion order to storage-service");
        }
    }

    public void deletePaperNoCheck(Long id) throws PaperNotFoundException {
        Paper paper = paperQueryDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        paperDeletionDAO.deletePaper(paper);
    }

    public void emergencyDelete(String uuid) {
        try {
            Paper paper = this.paperQueryDAO.getPaperByUuid(uuid);
            this.paperDeletionDAO.deletePaper(paper);
        } catch (PaperNotFoundException e) {
            Log.warn(e.getMessage());
        }
    }
}
