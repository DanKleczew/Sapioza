package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PaperDeletionDAO {

    @PersistenceContext
    private EntityManager em;

    @Inject
    ReviewDAO reviewDAO;

    @Inject
    OpinionDAO opinionDAO;

    public void deletePaper(Paper paper) {
        try {
            this.reviewDAO.removeReviews(paper.getId());
            this.opinionDAO.removeOpinions(paper.getId());
            Paper managedPaper = em.find(Paper.class, paper.getId());
            em.remove(managedPaper);
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
    }
}
