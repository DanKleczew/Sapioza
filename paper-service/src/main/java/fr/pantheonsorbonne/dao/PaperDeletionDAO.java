package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dao.interfaces.DeletionDAOInterface;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PaperDeletionDAO implements DeletionDAOInterface {

    @PersistenceContext
    private EntityManager em;

    public void deletePaper(Paper paper) throws PaperDatabaseAccessException {
        try {
            em.remove(paper);
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
    }
}
