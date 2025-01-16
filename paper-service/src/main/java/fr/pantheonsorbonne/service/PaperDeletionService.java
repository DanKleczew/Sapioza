package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.dao.PaperDeletionDAO;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaperDeletionService {

    @Inject
    PaperDeletionDAO paperDeletionDAO;

    @Inject
    PaperQueryDAO paperQueryDAO;

    @Inject
    StorageGateway storageGateway;

    public void deletePaper(Long id) throws PaperNotFoundException,
                                            PaperDatabaseAccessException {
        Paper paper = paperQueryDAO.getPaper(id);
        if (paper == null) {
            throw new PaperNotFoundException(id);
        }
        paperDeletionDAO.deletePaper(paper);

        try {
            this.storageGateway.deletePaper(id);
        } catch (InternalCommunicationException e) {
            Log.error("Error while sending deletion order to storage-service");
        }
    }
}
