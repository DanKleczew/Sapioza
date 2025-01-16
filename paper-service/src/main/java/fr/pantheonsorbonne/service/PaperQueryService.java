package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.camel.gateway.UserGateway;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.*;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.mappers.PaperMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PaperQueryService {

    @Inject
    PaperMapper paperMapper;

    @Inject
    PaperQueryDAO paperQueryDAO;

    @Inject
    UserGateway userGateway;

    @Inject
    StorageGateway storageGateway;

    public QueriedPaperInfosDTO getPaperInfos(Long id) throws PaperNotFoundException, InternalCommunicationException {
        Paper paper = paperQueryDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        UserInfoDTO userInfosDTO = userGateway.getUserInfos(paper.getAuthorId());
        return new QueriedPaperInfosDTO(paperMapper.mapEntityToDTO(paper), userInfosDTO);
    }

    public List<PaperDTO> getFilteredPapers(FilterDTO filter) {
        return paperQueryDAO.getFilteredPapers(filter).stream().map(paperMapper::mapEntityToDTO).toList();
    }

    public CompletePaperDTO getCompletePaper(Long id) throws PaperNotFoundException, InternalCommunicationException {
        QueriedPaperInfosDTO queriedPaperInfosDTO = this.getPaperInfos(id);
        String uuid = this.paperQueryDAO.getPaper(id).getUuid();
        String body = this.storageGateway.getPaperContent(uuid);
        return new CompletePaperDTO(queriedPaperInfosDTO.paperDTO(),
                queriedPaperInfosDTO.userInfoDTO(), body);
    }
}
