package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.logging.Log;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationCreationService {

    @Inject
    NotificationDAO notificationDAO;

    public void persistNotifications(PaperMetaDataDTO paperMetaDataDTO,
                                     UserInfoDTO authorInfosDTO,
                                     UserFollowersDTO userFollowersDTO) {
        try {
            List<Notification> notifications = userFollowersDTO.followers().stream()
                    .map(follower-> new Notification(
                            follower.id(),
                            paperMetaDataDTO.PaperId(),
                            authorInfosDTO.firstName(),
                            authorInfosDTO.lastName(),
                            paperMetaDataDTO.title(),
                            paperMetaDataDTO.publicationDate()
                    ))
                    .collect(Collectors.toList());
            notificationDAO.persistAll(notifications);
        } catch (Exception e) {
            Log.error("Error persisting notifications for paper ID: " + paperMetaDataDTO.PaperId(), e);
        }
    }
}