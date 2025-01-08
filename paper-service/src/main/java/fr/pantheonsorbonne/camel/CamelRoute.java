package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.PersistFailureHandler;
import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

        @Inject
        RoutingService routingService;

        @Override
        public void configure(){

                // Publishing process for a new paper
                from(routingService.getLocalRoute(Routes.NEW_TO_NOTIF))
                        .to(routingService.getGlobalRoute(GlobalRoutes.NEW_PAPER_P2N));

                from(routingService.getLocalRoute(Routes.NEW_TO_STORAGE))
                        .to(routingService.getGlobalRoute(GlobalRoutes.NEW_PAPER_P2S));

                // Fallback process for failed persistence in any of the two services
                from(routingService.getGlobalRoute(GlobalRoutes.PERSIST_FAIL_N2P))
                        .bean(PersistFailureHandler.class, "handle(${id})");

                from(routingService.getGlobalRoute(GlobalRoutes.PERSIST_FAIL_S2P))
                        .bean(PersistFailureHandler.class, "handle(${id})");

                // Deleting process for a paper
                from(routingService.getLocalRoute(Routes.DELETE_COMMAND_TO_STORAGE))
                        .to(routingService.getGlobalRoute(GlobalRoutes.DELETE_PAPER_P2S));



//            from("timer:foo?period=100000000000")
//                    .log("Création d'un PDF...")
//                    .setBody(constant("Bonjour, ceci est un exemple de PDF généré avec Apache Camel!"))
//                    .to("pdf:create")
//                    .to("file:output?fileName=example.pdf")
//                    .log("PDF créé et enregistré dans le dossier 'output'.")
//                    .bean(;
        }
}
