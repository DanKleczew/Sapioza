package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.GlobalRoutes;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

    @Inject
    StoredPaperService storageService;

    @Inject
    StoredPaperProcessor paperProcessor;

    @Override
    public void configure() {
        // Route pour sauvegarder un papier
        from(GlobalRoutes.NEW_PAPER_P2S.getRoute())
                .log("Received new paper: ${body}")
                .process(paperProcessor);
//                .bean(storageService, "savePaper")
//                .onException(Exception.class)
//                .handled(true)
//                .log("Error while saving paper: ${exception.message}");

        // Route pour récupérer le contenu d'un papier
        from(Routes.SEND_PAPER_CONTENT.getRoute())
                .log("Fetching content for paper UUID: ${body}")
                .bean(storageService, "getPaperContent")
                .onException(Exception.class)
                .handled(true)
                .log("Error while fetching paper content: ${exception.message}")
                .setBody(constant("Error fetching content"));
    }
}

