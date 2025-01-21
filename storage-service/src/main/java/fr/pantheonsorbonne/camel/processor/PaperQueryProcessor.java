package fr.pantheonsorbonne.camel.processor;

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
    public void process(Exchange exchange) {
        String paperUuid = exchange.getMessage().getBody(String.class);
        try {
            byte[] pdf = storedPaperService.getPaperContent(paperUuid);
            exchange.getMessage().setHeader("paperFound", true);
            exchange.getMessage().setBody(pdf);
        } catch (PaperNotFoundException e) {
            exchange.getMessage().setHeader("paperFound", false);
            exchange.getMessage().setBody("Paper not found");
        }

    }
}
