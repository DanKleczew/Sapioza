package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PaperDeletionDAO {

    @PersistenceContext
    private EntityManager em;

    public void deletePaper(Paper paper) {
        try {
            Paper managedPaper = em.find(Paper.class, paper.getId());
            em.remove(managedPaper);
        } catch (RuntimeException re) {
            Log.error(re.getMessage());
            throw new PaperDatabaseAccessException();
        }
    }
}
