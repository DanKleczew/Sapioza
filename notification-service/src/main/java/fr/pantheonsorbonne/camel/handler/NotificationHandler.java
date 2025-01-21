package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.camel.gateway.UserNotificationGateway;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.service.NotificationCreationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;

import java.util.List;

@ApplicationScoped
public class NotificationHandler {

    @Inject
    NotificationCreationService notificationCreationService;

    @Inject
    UserNotificationGateway userNotificationGateway;

    @Inject
    CamelContext camelContext;

    @Handler
    public void handle(PaperMetaDataDTO paperMetaDataDTO) {
        // Toutes les infos des followers de l'auteur du papier
        UserFollowersDTO userFollowersDTO = this.userNotificationGateway.getUserFollowers(paperMetaDataDTO.authorId());
        // Les informations de l'auteur du papier
        UserInfoDTO userInfoDTO = this.userNotificationGateway.getUserInfo(paperMetaDataDTO.authorId());


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
