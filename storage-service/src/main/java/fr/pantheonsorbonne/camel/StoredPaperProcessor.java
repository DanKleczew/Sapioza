package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class StoredPaperProcessor implements Processor {

    @Inject
    StoredPaperService storedPaperService;

    @Override
    public void process(Exchange exchange) throws Exception {
        PaperContentDTO message = exchange.getMessage().getBody(PaperContentDTO.class);
        storedPaperService.savePaper(message);
    }
}
