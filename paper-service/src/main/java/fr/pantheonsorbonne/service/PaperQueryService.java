package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.PaperDeletionDAO;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.*;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
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

    public PaperDTO getPaperInfos(Long id) throws PaperNotFoundException, PaperDatabaseAccessException {
        Paper paper = paperQueryDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        return paperMapper.mapEntityToDTO(paper);
    }

    public List<PaperDTO> getFilteredPapers(FilterDTO filter) {
        return paperQueryDAO.filterPapers(filter).stream().map(paperMapper::mapEntityToDTO).toList();
    }
}
