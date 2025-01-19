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

    @Inject
    PaperQueryProcessor paperQueryProcessor;

    @Inject
    UpdateProcessor updateProcessor;

    @Inject
    DeleteProcessor deleteProcessor;

    @Override
    public void configure() {
        // Route pour sauvegarder un papier
        from(GlobalRoutes.NEW_PAPER_P2S.getRoute())
                .log("Received new paper: ${body}")
                .process(paperProcessor);

        from(GlobalRoutes.ALTER_PAPER_P2S.getRoute())
                .log("Received alter command: ${header.command}")
                .choice()
                .when(header("command").isEqualTo("update"))
                    .log("Processing update for paper")
                    .process(updateProcessor)
                .when(header("command").isEqualTo("delete"))
                    .log("Processing delete for paper")
                    .process(deleteProcessor)
                .otherwise()
                    .log("Unknown command received: ${header.command}")
                    .stop();

        from(GlobalRoutes.PAPER_CONTENT_REQUEST_REPLY_QUEUE.getRoute())
                .log("Received paper query for ${body}")
                .process(paperQueryProcessor);

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

