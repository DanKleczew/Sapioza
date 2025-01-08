package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.camel.gateway.NotificationGateway;
import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.dao.PaperDAO;
import fr.pantheonsorbonne.dto.*;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotCreatedException;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.mappers.PaperMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PaperService {

    @Inject
    PaperDAO paperDAO;

    @Inject
    PaperMapper paperMapper;

    @Inject
    NotificationGateway notificationGateway;

    @Inject
    StorageGateway storageGateway;

    public PaperDTO getPaperInfos(Long id) throws PaperNotFoundException, PaperDatabaseAccessException {
        Paper paper = paperDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        return paperMapper.mapEntityToDTO(paper);
    }

    public List<PaperDTO> getFilteredPapers(FilterDTO filter) {
        return paperDAO.filterPapers(filter).stream().map(paperMapper::mapEntityToDTO).toList();
    }

    public PaperMetaDataDTO createPaper(CompletePaperDTO completePaperDTO) throws PaperNotCreatedException,
                                                                                  PaperDatabaseAccessException {
        Paper paper = paperMapper.mapDTOToEntity(completePaperDTO.metaData());

        paper = paperDAO.createPaper(paper);
        if (paper.getId() == null) {
            throw new PaperNotCreatedException();
        }

        PaperMetaDataDTO paperMetaDataDTO = this.mapPaperToPaperMetaDataDTO(paper);
        try {
            // Send Paper Metadata to notification-service
            this.notificationGateway.newPaper(paperMetaDataDTO);
            PaperContentDTO paperContentDTO = new PaperContentDTO(paper.getId(), completePaperDTO.body());

            // Send Paper Content to storage-service
            this.storageGateway.newPaper(paperContentDTO);
        } catch (InternalCommunicationException e) {
            // Rollback
            try {
                this.deletePaper(paper.getId());
            } catch (PaperNotFoundException ex) {
                Log.warn("Paper not found while attempting to delete: " + paper.getId());
            }
            throw new PaperNotCreatedException();
        }
        return paperMetaDataDTO;
    }

    public void deletePaper(Long id) throws PaperNotFoundException,
                                            PaperDatabaseAccessException {
        Paper paper = paperDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        paperDAO.deletePaper(paper);

        try {
            this.storageGateway.deletePaper(id);
        } catch (InternalCommunicationException e) {
            Log.error("Error while sending deletion order to storage-service");
        }
    }

    private PaperMetaDataDTO mapPaperToPaperMetaDataDTO(Paper paper) {
        return new PaperMetaDataDTO(
            paper.getId(),
            paper.getTitle(),
            paper.getAuthorId(),
            paper.getPublicationDate()
        );
    }
}
