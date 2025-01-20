package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class PersistenceProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getMessage().getBody(PaperMetaDataDTO.class);

    }
}

