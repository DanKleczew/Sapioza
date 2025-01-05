package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.PaperDAO;
import fr.pantheonsorbonne.entity.Paper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PaperService {

    @Inject
    PaperDAO paperDAO;


    public Paper getPaperInfos(long id) throws PaperNotFoundException {

        Paper paper = null;
        paper = paperDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        return paper;
    }
}
