package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.exception.PaperNotFoundException;
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
    public StoredPaper saveStoredPaper(StoredPaper storedPaper) {
        // Check if the StoredPaper has a UUID
        if (storedPaper.getPaperUuid() != null) {
            // If the UUID exists, search for the paper by paperUuid
            StoredPaper existing = entityManager
                    .createQuery("SELECT sp FROM StoredPaper sp WHERE sp.paperUuid = :paperUuid", StoredPaper.class)
                    .setParameter("paperUuid", storedPaper.getPaperUuid())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existing != null) {
                // If found, update its body and merge it
                existing.setBody(storedPaper.getBody());
                return entityManager.merge(existing);
            } else {
                // If no paper is found with the given UUID, persist it as a new entity
                entityManager.persist(storedPaper);
                return storedPaper;
            }
        } else {
            // If paperUuid is null, persist the new paper without UUID
            entityManager.persist(storedPaper);
            return storedPaper;
        }
    }

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
