package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.mapper.NotificationEntityDtoMapper;
import fr.pantheonsorbonne.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import io.quarkus.logging.Log;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationCreationService {

    @Inject
    NotificationDAO notificationDAO;

    @Inject
    ProducerTemplate producerTemplate;

    @Inject
    NotificationEntityDtoMapper mapper;

    public void persistNotifications(PaperMetaDataDTO paperMetaDataDTO, UserFollowersDTO userFollowersDTO) {
        try {
            List<Notification> notifications = userFollowersDTO.followersId().stream()
                    .map(followerId -> new Notification(
                            followerId,
                            paperMetaDataDTO.PaperId(),
                            paperMetaDataDTO.authorId(),
                            paperMetaDataDTO.title(),
                            paperMetaDataDTO.publicationDate()
                    ))
                    .collect(Collectors.toList());
            notificationDAO.persistAll(notifications);
        } catch (Exception e) {
            Log.error("Error persisting notifications for paper ID: " + paperMetaDataDTO.PaperId(), e);
            throw e;
        }
    }
}