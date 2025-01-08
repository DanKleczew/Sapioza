package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.service.PaperService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersistFailureHandler {

    @Inject
    PaperService paperService;

    public void handle(Long id) {
        try {
            paperService.deletePaper(id);
        } catch (Exception e) {
            Log.error("Error while deleting paper", e);
        }
    }
}
