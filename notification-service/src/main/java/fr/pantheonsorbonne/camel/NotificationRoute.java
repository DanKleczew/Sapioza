package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.NotificationHandler;
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
    NotificationHandler notificationHandler;

    @Override
    public void configure() {
        // Gestion des exceptions dans toutes les routes
        onException(Exception.class)
                .log("Erreur lors du traitement de la notification : ${exception.message}")
                .handled(true);

        from(GlobalRoutes.NEW_PAPER_P2N.getRoute()) //bon
                .log("Message reçu pour notification d'un nouvel article : ${body}")
                .bean(notificationHandler);

        from(Routes.GET_USER_INFO.getRoute()) //bon
                .setExchangePattern(ExchangePattern.InOut)
                .to(GlobalRoutes.USER_INFO_REQUEST_REPLY_QUEUE.getRoute()+ "?requestTimeout=5000")
                .end();

        from(Routes.GET_USER_FOLLOWERS.getRoute())
                .setExchangePattern(ExchangePattern.InOut)
                .to(GlobalRoutes.GET_USER_FOLLOWERS.getRoute()+ "?requestTimeout=5000")
                .end();


        //méthode pour recup info dan et
        //demande info raymon


        //recupère objet dans le handler dont authorId et j'appelle ma 3e route pour avoir tableau avec followers

        //appelle service pour tout écrire avec le dao dans bdd

    }
}
