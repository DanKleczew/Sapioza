package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.global.GlobalRoutes;
import fr.pantheonsorbonne.service.NotificationCreationService;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
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
        // Gestion des exceptions dans la route
        onException(Exception.class)
                .log("Erreur lors du traitement de la notification : ${exception.message}")
                .handled(true);

        // Consommation des messages depuis la queue
        from(GlobalRoutes.NEW_PAPER_P2N.getRoute())
                .routeId("NotificationRoute")
                .log("Message reçu sur la queue JMS : ${body}")
                .process(this::processNotification); // Traitement de la notification

        // Route interne pour traiter les métadonnées du papier
        from("direct:processNotification")
                .routeId("ProcessPaperMetaDataRoute")
                .process(exchange -> {
                    PaperMetaDataDTO metaData = exchange.getIn().getBody(PaperMetaDataDTO.class);
                    notificationCreationService.processNotification(metaData);
                });
    }

    /**
     * Traite les messages pour créer une notification à partir d'un DTO NotificationDTO.
     *
     * @param exchange L'échange contenant les données du message.
     */
    private void processNotification(Exchange exchange) {
        // Extraction des données depuis le message
        String authorName = exchange.getIn().getHeader("authorName", String.class);
        String paperTitle = exchange.getIn().getBody(String.class);

        if (authorName == null || paperTitle == null) {
            throw new IllegalArgumentException("Missing required fields: authorName or paperTitle");
        }

        // Création d'un DTO pour la notification
        NotificationDTO dto = new NotificationDTO();
        dto.setAuthorName(authorName);
        dto.setPaperTitle(paperTitle);

        // Appel au service pour créer la notification
        notificationCreationService.createNotification(dto);
    }
}
