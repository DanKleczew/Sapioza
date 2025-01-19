package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dao.interfaces.QueryDAOInterface;
import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class PaperQueryDAO implements QueryDAOInterface {

    @PersistenceContext
    private EntityManager em;

    public Paper getPaper(Long id) throws PaperDatabaseAccessException {
        Paper paper = null;
        try {
            paper = em.find(Paper.class, id);

        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return paper;
    }

    public List<Paper> getFilteredPapers(FilterDTO filter) {
        try {

            //this.em.createQuery()
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return null;
    }

}
