package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.PersistFailureHandler;
import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;


@ApplicationScoped
public class CamelRoute extends RouteBuilder {

        @Inject
        PersistFailureHandler persistFailureHandler;

        @Override
        public void configure(){
                // Publishing process for a new paper
                from(Routes.NEW_TO_NOTIF.getRoute())
                        .to(GlobalRoutes.NEW_PAPER_P2N.getRoute());

                from(Routes.NEW_TO_STORAGE.getRoute())
                        .to(GlobalRoutes.NEW_PAPER_P2S.getRoute());

                // Fallback process for failed persistence in storage-service
                from(GlobalRoutes.PERSIST_FAIL_S2P.getRoute())
                        .bean(persistFailureHandler);

                // Deleting process for a paper
                from(Routes.DELETE_COMMAND_TO_STORAGE.getRoute())
                        .setHeader("command", constant("delete"))
                        .to(GlobalRoutes.ALTER_PAPER_P2S.getRoute());

                // Updating process for a paper
                from(Routes.UPDATE_TO_STORAGE.getRoute())
                        .setHeader("command", constant("update"))
                        .to(GlobalRoutes.ALTER_PAPER_P2S.getRoute());

                //Fetching user basic info process
                from(Routes.REQUEST_USER_INFO.getRoute())
                        .setExchangePattern(ExchangePattern.InOut)
                        .to(GlobalRoutes.USER_INFO_REQUEST_REPLY_QUEUE.getRoute()
                                + "?requestTimeout=5000")
                        .end();

                // Requesting check for user strong identification process
                from(Routes.REQUEST_USER_STRONG_IDENTIFICATION.getRoute())
                        .setExchangePattern(ExchangePattern.InOut)
                        .to(GlobalRoutes.USER_STRONG_ID_REQUEST_REPLY_QUEUE.getRoute()
                                + "?requestTimeout=5000")
                        .end();

                //Fetching paper content process
                from(Routes.GET_PAPER_CONTENT.getRoute())
                        .setExchangePattern(ExchangePattern.InOut)
                        .to(GlobalRoutes.PAPER_CONTENT_REQUEST_REPLY_QUEUE.getRoute()
                                + "?requestTimeout=5000")
                        .end();
        }
}
