package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.mappers.StoredPaperMapper;
import fr.pantheonsorbonne.model.StoredPaper;
import io.quarkus.arc.impl.InjectableRequestContextController;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StoredPaperDAO {

    private final InjectableRequestContextController injectableRequestContextController;
    @PersistenceContext
    EntityManager entityManager;

    @jakarta.inject.Inject
    public StoredPaperDAO(InjectableRequestContextController injectableRequestContextController) {
        this.injectableRequestContextController = injectableRequestContextController;
    }

    @Transactional
    public StoredPaper saveStoredPaper(StoredPaper storedPaper) {
        if (storedPaper.getId() != null) {
            // Si un ID est déjà présent, on met à jour l'entité existante
            StoredPaper existing = entityManager.find(StoredPaper.class, storedPaper.getId());
            if (existing != null) {
                // Si l'entité existe, on met à jour son contenu
                existing.setBody(storedPaper.getBody());
                return entityManager.merge(existing);
            } else {
                throw new IllegalArgumentException("No entity found with ID: " + storedPaper.getId());
            }
        } else {
            // Si l'ID est nul, on persiste une nouvelle entité avec un ID généré automatiquement
            entityManager.persist(storedPaper);
            return storedPaper;
        }
    }

    public StoredPaper findStoredPaperById(Long id) {
        return entityManager.find(StoredPaper.class, id);
    }

    @Transactional
    public void deleteStoredPaper(StoredPaper storedPaper) {
        entityManager.remove(storedPaper);
    }

}
