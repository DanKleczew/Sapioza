package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class UpdateProcessor implements Processor {

    @Inject
    StoredPaperService storedPaperService;

    @Override
    public void process(Exchange exchange) throws PaperNotFoundException {
        PaperContentDTO message = exchange.getMessage().getBody(PaperContentDTO.class);
        storedPaperService.updateStoredPaper(message);
    }
}
