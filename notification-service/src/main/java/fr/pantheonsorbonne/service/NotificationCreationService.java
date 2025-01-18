package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.mapper.NotificationEntityDtoMapper;
import fr.pantheonsorbonne.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import io.quarkus.logging.Log;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NotificationCreationService {

    @Inject
    NotificationDAO notificationDAO;

    @Inject
    ProducerTemplate producerTemplate;

    @Inject
    NotificationEntityDtoMapper mapper;

    private static final String GET_FOLLOWERS_ROUTE = "direct:getFollowers";

    /**
     * Crée une notification pour un utilisateur et un article donnés.
     *
     * @param userId  ID de l'utilisateur à notifier.
     * @param paperId ID de l'article lié à la notification.
     * @param authorName Nom de l'auteur de l'article.
     */
    public void createNotification(Long userId, Long paperId, String authorName) {
        try {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setPaperId(paperId);
            notification.setAuthorName(authorName);
            notification.setNotificationTime(LocalDateTime.now());
            notification.setViewed(false);

            notificationDAO.create(notification);
            Log.info("Notification created for user ID: " + userId);
        } catch (Exception e) {
            Log.error("Failed to create notification for user ID: " + userId, e);
        }
    }

    /**
     * Récupère les followers d'un auteur à partir du User Service.
     *
     * @param authorId ID de l'auteur.
     * @return Liste des IDs des followers.
     */
    public List<Long> getFollowers(Long authorId) {
        try {
            List<?> rawList = producerTemplate.requestBody(
                    GET_FOLLOWERS_ROUTE,
                    authorId,
                    List.class
            );

            return rawList.stream()
                    .filter(item -> item instanceof Long)
                    .map(item -> (Long) item)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch followers for author ID: " + authorId, e);
        }
    }

    /**
     * Crée des notifications pour tous les followers d'un auteur donné pour un article.
     *
     * @param authorId ID de l'auteur.
     * @param paperId  ID de l'article publié.
     * @param authorName Nom de l'auteur.
     */
    public void notifyFollowers(Long authorId, Long paperId, String authorName) {
        List<Long> followers = getFollowers(authorId);
        if (followers.isEmpty()) {
            Log.info("No followers found for author ID: " + authorId);
            return;
        }

        followers.forEach(followerId -> createNotification(followerId, paperId, authorName));
        Log.info("Notifications successfully sent for paper ID: " + paperId + " by author ID: " + authorId);
    }


}
