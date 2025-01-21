package fr.pantheonsorbonne.dao.interfaces;

import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.model.Paper;

import java.util.List;

public interface QueryDAOInterface {
    Paper getPaper(Long id);

    List<Paper> getFilteredPapers(FilterDTO filter);

    Paper getPaperByUuid(String uuid) throws PaperNotFoundException;
}
