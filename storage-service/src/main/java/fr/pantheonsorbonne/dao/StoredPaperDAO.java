package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.model.StoredPaper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StoredPaperDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public StoredPaper saveStoredPaper(StoredPaper storedPaper) {
        entityManager.persist(storedPaper);
        return storedPaper;
    }

    public StoredPaper findStoredPaperById(Long id) {
        return entityManager.find(StoredPaper.class, id);
    }
}
