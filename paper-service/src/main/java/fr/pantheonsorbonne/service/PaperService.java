package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.PaperDAO;
import fr.pantheonsorbonne.dto.Filter;
import fr.pantheonsorbonne.entity.Paper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PaperService {

    @Inject
    PaperDAO paperDAO;

    public Paper getPaperInfos(long id) throws PaperNotFoundException {
        Paper paper = paperDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        return paper;
    }

    public List<Paper> getFilteredPapers(Filter filter) {
        return paperDAO.filter(filter);
    }
}
