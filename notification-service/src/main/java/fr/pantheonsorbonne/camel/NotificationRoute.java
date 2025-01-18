package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.GlobalRoutes;
import fr.pantheonsorbonne.service.NotificationCreationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class NotificationRoute extends RouteBuilder {

    @Inject
    NotificationCreationService notificationCreationService;

    @Override
    public void configure() {
        // Gestion des exceptions dans toutes les routes
        onException(Exception.class)
                .log("Erreur lors du traitement de la notification : ${exception.message}")
                .handled(true);

        from(GlobalRoutes.NEW_PAPER_P2N.getRoute())

                .log("Message reçu pour notification d'un nouvel article : ${body}");
                //.bean(notificationCreationService, "notifyFollowers(${body.authorId}, ${body.paperId}, ${body.authorName})");

        from(GlobalRoutes.USER_REQUEST_N2U.getRoute())
                .log("Demande d'abonnés pour l'utilisateur : ${body}")
                .to(GlobalRoutes.GET_USER_FOLLOWERS_U2N.getRoute());

       /* from(GlobalRoutes.USER_RESPONSE_U2N.getRoute())
                .log("Réponse reçue avec les abonnés : ${body}")
                .bean(notificationCreationService, "processUserFollowers(${body})");*/

        from(GlobalRoutes.USER_RESPONSE_U2N.getRoute())
                .log("Réponse reçue avec les abonnés : ${body}")
                .bean(notificationCreationService, "processUserFollowers(${body})")
                .end();



        from(Routes.GET_USER_INFO.getRoute())
                .setExchangePattern(ExchangePattern.InOut)
                .to(GlobalRoutes.USER_INFO_REQUEST_REPLY_QUEUE.getRoute()+ "?requestTimeout=5000")
                .end();


    }
}
