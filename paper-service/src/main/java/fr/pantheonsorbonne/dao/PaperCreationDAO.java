package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dao.interfaces.CreationDAOInterface;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PaperCreationDAO {

    @PersistenceContext
    private EntityManager em;

    public Paper createPaper(Paper paper) throws PaperDatabaseAccessException {
        try {
            em.persist(paper);
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return paper;
    }
}
