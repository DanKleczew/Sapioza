package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.GlobalRoutes;
import fr.pantheonsorbonne.service.NotificationCreationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;

@ApplicationScoped
public class NotificationRoute extends RouteBuilder {

    @Inject
    NotificationCreationService notificationCreationService;

    @Override
    public void configure() {
        onException(Exception.class)
                .log("Erreur lors du traitement de la notification : ${exception.message}")
                .handled(true);

        // Consommation des messages depuis la queue
        from(GlobalRoutes.NEW_PAPER_P2N.getRoute())
                .routeId("NotificationRoute")
                .log("Message reçu sur la queue JMS : ${body}")
                .process(this::processNotification);
    }

    /**
     * Traite les messages pour créer une notification.
     *
     * @param exchange L'échange contenant les données du message JMS.
     */
    private void processNotification(Exchange exchange) {
        // Extraction des données depuis le message
        String authorName = exchange.getIn().getHeader("authorName", String.class);
        String paperTitle = exchange.getIn().getBody(String.class);

        // Création de la notification via NotificationCreationService
        if (authorName != null && paperTitle != null) {
            notificationCreationService.createNotification(authorName, paperTitle);
        } else {
            throw new IllegalArgumentException("Les informations nécessaires à la notification sont manquantes");
        }
    }
}

