package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class PaperQueryProcessor implements Processor {

    @Inject
    StoredPaperService storedPaperService;

    @Override
    public void process(Exchange exchange) throws PaperNotFoundException {
        String message = exchange.getMessage().getBody(String.class);
        exchange.getIn().setBody(storedPaperService.getPaperContent(message).pdf());
    }
}
