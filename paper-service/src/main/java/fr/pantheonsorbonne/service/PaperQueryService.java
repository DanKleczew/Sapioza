package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.camel.gateway.UserGateway;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.*;
import fr.pantheonsorbonne.dto.PaperDTOs.CompleteQueriedPaperInfosDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.QueriedPDF;
import fr.pantheonsorbonne.dto.PaperDTOs.QueriedPaperInfosDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.mappers.PaperMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
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


    public QueriedPaperInfosDTO getPaperInfos(Long id) throws PaperNotFoundException {
        Paper paper = paperQueryDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        UserInfoDTO userInfosDTO = userGateway.getUserInfos(paper.getAuthorId());
        return new QueriedPaperInfosDTO(paperMapper.mapEntityToDTO(paper), userInfosDTO);
    }

    public List<CompleteQueriedPaperInfosDTO> getFilteredPapers(FilterDTO filter) {
        List<CompleteQueriedPaperInfosDTO> list = new ArrayList<>();
        List<Paper> papers = paperQueryDAO.getFilteredPapers(filter);
        for (Paper paper : papers){
            list.add(
                    new CompleteQueriedPaperInfosDTO(
                            paper.getId(),
                            new QueriedPaperInfosDTO(
                                paperMapper.mapEntityToDTO(paper),
                                userGateway.getUserInfos(paper.getAuthorId())
                            )
                    )
            );
        }
        return list;
        //paperQueryDAO.getFilteredPapers(filter).stream().map(paperMapper::mapPaperToPaperMetaDataDTO).toList();
    }

    public QueriedPDF getPDF(Long id) throws PaperNotFoundException, InternalCommunicationException {
        Paper paper = paperQueryDAO.getPaper(id);
        return new QueriedPDF(
                paper.getTitle(),
                this.storageGateway.getPDF(paper.getUuid())
        );
    }
}
