package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

    private final StoredPaperService storageService;

    public CamelRoute(StoredPaperService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void configure() {
        // Route pour sauvegarder un papier
        from(Routes.NEW_TO_STORAGE.getRoute())
                .log("Received new paper: ${body}")
                .bean(storageService, "savePaper")
                .onException(Exception.class)
                .handled(true)
                .log("Error while saving paper: ${exception.message}");

        // Route pour récupérer le contenu d'un papier
        from(Routes.GET_PAPER_CONTENT.getRoute())
                .log("Fetching content for paper UUID: ${body}")
                .bean(storageService, "getPaperContent")
                .onException(Exception.class)
                .handled(true)
                .log("Error while fetching paper content: ${exception.message}")
                .setBody(constant("Error fetching content"));
    }
}

