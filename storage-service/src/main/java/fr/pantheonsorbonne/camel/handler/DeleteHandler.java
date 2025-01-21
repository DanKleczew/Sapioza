package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.service.StoredPaperService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Processor;

@ApplicationScoped
public class DeleteHandler {

    @Inject
    StoredPaperService storedPaperService;

    @Handler
    public void handle(Exchange exchange)  {
        String message = exchange.getMessage().getBody(String.class);
        try {
            storedPaperService.deleteStoredPaper(message);
        } catch (PaperNotFoundException e) {
            Log.error("The paper with the following uuid was not found: " + message);
        }
    }
}
