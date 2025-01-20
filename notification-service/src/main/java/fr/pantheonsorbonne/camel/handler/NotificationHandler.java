package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.service.NotificationCreationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class NotificationHandler {


    @Inject
    NotificationCreationService notificationCreationService;

    @Inject
    CamelContext camelContext;

    @Handler
    public void handle(PaperMetaDataDTO paperMetaDataDTO) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            UserFollowersDTO userFollowersDTO = producerTemplate
                    .requestBody(Routes.GET_USER_FOLLOWERS.getRoute(),
                        paperMetaDataDTO.authorId(), UserFollowersDTO.class);

            notificationCreationService.persistNotifications(paperMetaDataDTO, userFollowersDTO);

        } catch (Exception e) {
            Log.error("Error while processing notifications for author ID: " + paperMetaDataDTO, e);
        }
    }
}
