package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.entity.NotificationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;

import java.time.LocalDateTime;

@ApplicationScoped
public class NotificationCreationService {

    @Inject
    NotificationDAO notificationDAO;

    @Inject
    ProducerTemplate producerTemplate;

    private static final String NOTIFICATION_QUEUE = "jms:queue:notificationQueue";

    /**
     * Crée une notification en base de données et la place dans une queue pour traitement ultérieur.
     *
     * @param authorName Le nom de l'auteur.
     * @param paperTitle Le titre du papier.
     */
    public void createNotification(String authorName, String paperTitle) {
        // Création de l'entité Notification
        NotificationEntity notification = new NotificationEntity();
        notification.setAuthorName(authorName);
        notification.setPaperTitle(paperTitle);
        notification.setNotificationTime(LocalDateTime.now());

        // Sauvegarde en base de données
        notificationDAO.create(notification);

        // Envoi de la notification dans une queue JMS
        producerTemplate.sendBodyAndHeader(
                NOTIFICATION_QUEUE,
                notification,
                "notificationType",
                "NEW_PAPER"
        );
    }
}
