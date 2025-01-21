package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.camel.gateway.UserNotificationGateway;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.service.NotificationCreationService;
import fr.pantheonsorbonne.camel.gateway.MailingGateway;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;


@ApplicationScoped
public class NotificationHandler {

    @Inject
    NotificationCreationService notificationCreationService;

    @Inject
    UserNotificationGateway userNotificationGateway;

    @Inject
    MailingGateway mailingGateway;

    @Inject
    CamelContext camelContext;

    @Handler
    public void handle(PaperMetaDataDTO paperMetaDataDTO) {
        // Toutes les infos des followers de l'auteur du papier
        UserFollowersDTO userFollowersDTO = this.userNotificationGateway.getUserFollowers(paperMetaDataDTO.authorId());
        // Les informations de l'auteur du papier
        UserInfoDTO authorInfoDTO = this.userNotificationGateway.getUserInfo(paperMetaDataDTO.authorId());

        for (UserInfoDTO user : userFollowersDTO.followers()){
            this.mailingGateway.sendMail(user.email(), paperMetaDataDTO, authorInfoDTO);
        }
        notificationCreationService.persistNotifications(paperMetaDataDTO, authorInfoDTO, userFollowersDTO);
    }
}
