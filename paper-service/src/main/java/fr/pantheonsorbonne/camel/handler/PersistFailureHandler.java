package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.service.PaperDeletionService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Handler;

@ApplicationScoped
public class PersistFailureHandler implements HandlerInterface{

    @Inject
    PaperDeletionService paperDeletionService;

    @Handler
    public void handle(Long id) {
        try {
            paperDeletionService.deletePaperNoCheck(id);
        } catch (PaperNotFoundException e) {
            Log.warn("Error while deleting paper", e);
        }
    }
}
