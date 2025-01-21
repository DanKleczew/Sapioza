package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.camel.gateway.StorageGateway;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Handler;

@ApplicationScoped
public class SavingPaperHandler {

    @Inject
    StoredPaperService storedPaperService;

    @Inject
    StorageGateway storageGateway;

    @Handler
    public void handle(PaperContentDTO paperContentDTO) {
        try {
            storedPaperService.savePaper(paperContentDTO);
        } catch (RuntimeException e) {
            this.storageGateway.persistFailed(paperContentDTO);
        }
    }
}
