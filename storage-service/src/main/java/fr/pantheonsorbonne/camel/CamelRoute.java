package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.DeleteHandler;
import fr.pantheonsorbonne.camel.handler.SavingPaperHandler;
import fr.pantheonsorbonne.camel.handler.UpdateHandler;
import fr.pantheonsorbonne.camel.processor.PaperQueryProcessor;
import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

    @Inject
    SavingPaperHandler paperHandler;

    @Inject
    PaperQueryProcessor paperQueryProcessor;

    @Inject
    UpdateHandler updateHandler;

    @Inject
    DeleteHandler deleteHandler;

    @Override
    public void configure() {
        // Route pour sauvegarder un papier
        from(GlobalRoutes.NEW_PAPER_P2S.getRoute())
                .bean(paperHandler);

        // Route pour gérer modification ou suppression d'un papier
        from(GlobalRoutes.ALTER_PAPER_P2S.getRoute())
                .log("Received alter command: ${header.command}")
                .choice()
                .when(header("command").isEqualTo("update"))
                    .log("Processing update for paper")
                    .bean(updateHandler)
                .when(header("command").isEqualTo("delete"))
                    .log("Processing delete for paper")
                    .bean(deleteHandler)
                .otherwise()
                    .log("Unknown command received: ${header.command}")
                .stop();

        // Route pour répondre à une requête de papier
        from(GlobalRoutes.PAPER_CONTENT_REQUEST_REPLY_QUEUE.getRoute())
                .log("Received paper query for ${body}")
                .process(paperQueryProcessor)
                .end();

        // Route pour gérer les erreurs de sauvegarde
        from(Routes.PAPER_PERSIST_FAILURE.getRoute())
                .to(GlobalRoutes.PERSIST_FAIL_S2P.getRoute());
    }


}

