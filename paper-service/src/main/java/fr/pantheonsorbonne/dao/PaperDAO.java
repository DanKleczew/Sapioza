package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class PaperDAO {

    @PersistenceContext
    EntityManager em;

    public Paper getPaper(Long id) throws PaperDatabaseAccessException {
        Paper paper = null;
        try {
            paper = em.find(Paper.class, id);

        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return paper;
    }

    public List<Paper> filterPapers(FilterDTO filter) {
        List<Paper> papers = null;
        String query = "SELECT p FROM Paper p WHERE ";
        if (filter.title() != null) {
            query += "p.title = '" + filter.title() + "' AND ";
        }
        if (filter.authorId() != null) {
            query += "p.author = '" + filter.authorId() + "' AND ";
        }
        if (filter.abstract_() != null) {
            query += "p.abstract = '" + filter.abstract_() + "' AND ";
        }
        if (filter.keywords() != null) {
            query += "p.keywords = '" + filter.keywords() + "' AND ";
        }
        if (filter.revue() != null) {
            query += "p.revue = '" + filter.revue() + "' AND ";
        }
        if (filter.field() != null) {
            query += "p.field = '" + filter.field() + "' AND ";
        }

        if (query.endsWith("AND ")) {
            query = query.substring(0, query.length() - 5);
        } else {
            query = query.substring(0, query.length() - 7);
        }

        try {
            papers = em.createQuery(query, Paper.class).getResultList();
        } catch (RuntimeException re) {
            Log.error("getFilteredPapers failed", re);
        }
        return papers;
    }

    public Paper createPaper(Paper paper) throws PaperDatabaseAccessException {
        try {
            em.persist(paper);
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return paper;
    }

    public void deletePaper(Paper paper) throws PaperDatabaseAccessException {
        try {
            em.remove(paper);
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
    }
}
