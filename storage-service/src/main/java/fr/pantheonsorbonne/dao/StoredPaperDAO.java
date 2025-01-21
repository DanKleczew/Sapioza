package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.model.StoredPaper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StoredPaperDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveStoredPaper(StoredPaper storedPaper) {
        StoredPaper existing = entityManager
                .createQuery("SELECT sp FROM StoredPaper sp WHERE sp.paperUuid = :paperUuid", StoredPaper.class)
                .setParameter("paperUuid", storedPaper.getPaperUuid())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setBody(storedPaper.getBody());
            entityManager.merge(existing);
        } else {
            entityManager.persist(storedPaper);
        }
    }

    @Transactional
    public byte[] findBodyByUuid(String paperUuid) {
        return this.entityManager
                .createQuery("SELECT sp.body FROM StoredPaper sp WHERE sp.paperUuid = :paperUuid",byte[].class)
                .setParameter("paperUuid", paperUuid)
                .getSingleResult();
    }

    @Transactional
    public StoredPaper findStoredPaperByUuid(String paperUuid) {
        return entityManager
                .createQuery("SELECT sp FROM StoredPaper sp WHERE sp.paperUuid = :paperUuid", StoredPaper.class)
                .setParameter("paperUuid", paperUuid)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void deleteStoredPaper(StoredPaper storedPaper) {
        entityManager.remove(storedPaper);
    }

}
