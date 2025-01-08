package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.NotificationGateway;
import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.dao.PaperDAO;
import fr.pantheonsorbonne.dto.*;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotCreatedException;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.mappers.PaperMapper;
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
        this.notificationGateway.sendToNotification(paperMetaDataDTO);

        PaperBodyDTO paperBodyDTO = new PaperBodyDTO(paper.getId(), completePaperDTO.body());
        this.storageGateway.sendToStorage(paperBodyDTO);

        return paperMetaDataDTO;
    }

    public void deletePaper(Long id) throws PaperNotFoundException,
                                            PaperDatabaseAccessException {
        Paper paper = paperDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        paperDAO.deletePaper(paper);
    }

    private PaperMetaDataDTO mapPaperToPaperMetaDataDTO(Paper paper) {
        return new PaperMetaDataDTO(
            paper.getId(),
            paper.getTitle(),
            paper.getAuthorId(),
            paper.getField(),
            paper.getPublicationDate()
        );
    }
}
