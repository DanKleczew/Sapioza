package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.PersistFailureHandler;
import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

        @Override
        public void configure(){

                // Publishing process for a new paper
                from(Routes.NEW_TO_NOTIF.getRoute())
                        .to(GlobalRoutes.NEW_PAPER_P2N.getRoute());

                from(Routes.NEW_TO_STORAGE.getRoute())
                        .to(GlobalRoutes.NEW_PAPER_P2S.getRoute());

                // Fallback process for failed persistence storage-service
                from(GlobalRoutes.PERSIST_FAIL_S2P.getRoute())
                        .bean(PersistFailureHandler.class, "handle(${id})");

                // Deleting process for a paper
                from(Routes.DELETE_COMMAND_TO_STORAGE.getRoute())
                        .to(GlobalRoutes.DELETE_PAPER_P2S.getRoute());
        }
}
