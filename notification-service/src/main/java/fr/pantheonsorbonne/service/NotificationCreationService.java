package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.entity.NotificationEntity;
import fr.pantheonsorbonne.mapper.NotificationEntityDtoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


import fr.pantheonsorbonne.global.PaperMetaDataDTO;

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
    @Inject
    NotificationEntityDtoMapper mapper;

    public void createNotification(NotificationDTO dto) {
        // Convertir le DTO en entité
        NotificationEntity entity = mapper.mapToEntity(dto);

        // Ajouter des champs spécifiques si nécessaire
        entity.setNotificationTime(LocalDateTime.now());

        // Sauvegarder l'entité
        notificationDAO.create(entity);

        // Envoi de la notification dans une queue JMS
        producerTemplate.sendBodyAndHeader(
                NOTIFICATION_QUEUE,
                entity,
                "notificationType",
                "NEW_PAPER"
        );
    }

    /**
     * Récupère la liste des followers d'un auteur via une route Camel.
     *
     * @param authorId l'ID de l'auteur
     * @return une liste d'IDs des followers
     */

    private List<Long> getFollowers(Long authorId) {
        try {
            // Appel à la route Camel
            List<?> rawList = producerTemplate.requestBody(
                    "direct:getFollowers",
                    authorId,
                    List.class
            );

            // Vérifier et convertir les éléments en Long
            return rawList.stream()
                    .filter(item -> item instanceof Long) // Filtrer uniquement les Long
                    .map(item -> (Long) item) // Caster en Long
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch followers for author ID: " + authorId, e);
        }
    }


    //pour récupérer les followers sans forcément faire de gateway :

    public void processNotification(PaperMetaDataDTO metaData) {
        try {
            // Récupérer les followers de l'auteur
            List<Long> followers = getFollowers(metaData.authorId());

            // Créer les notifications en lot
            List<NotificationEntity> notifications = followers.stream().map(followerId -> {
                NotificationEntity notification = new NotificationEntity();
                notification.setUserId(followerId);
                notification.setAuthorName(metaData.authorId().toString());
                notification.setPaperTitle(metaData.title());
                notification.setNotificationTime(metaData.publicationDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                return notification;
            }).toList();

            // Persister les notifications
            for (NotificationEntity notification : notifications) {
                notificationDAO.create(notification);
            }

            // Log du succès
            System.out.println("Successfully processed notifications for PaperMetaDataDTO: " + metaData);

        } catch (Exception e) {
            // Log de l'erreur
            throw new RuntimeException("Failed to process notifications for PaperMetaDataDTO: " + metaData, e);
        }
    }




}




