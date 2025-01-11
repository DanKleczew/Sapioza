package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.entity.NotificationEntity;

import fr.pantheonsorbonne.mapper.NotificationEntityDtoMapper;
import fr.pantheonsorbonne.messaging.NotificationProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationDAO notificationDAO;
    @Inject
    NotificationEntityDtoMapper mapper;

    public List<NotificationEntity> getNotificationsByUserId(Long userId) {
        // Appelle la méthode du DAO pour récupérer les notifications de cet utilisateur
        return notificationDAO.findByUserId(userId);
    }

    @Inject
    NotificationProducer notificationProducer;

    public void createNotification(String authorName, String paperTitle) {
        // Créer une notification en base
        NotificationEntity notification = new NotificationEntity();
        notification.setAuthorName(authorName);
        notification.setPaperTitle(paperTitle);
        notification.setNotificationTime(LocalDateTime.now());
        notificationDAO.create(notification);

        // Envoyer un message via Artemis
        String message = "New paper published by " + authorName + ": " + paperTitle;
        notificationProducer.sendNotification(message);
    }


    /**
     * Récupère les notifications pour un utilisateur donné et les transforme en DTO.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une liste de NotificationDTO
     */
    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        // Récupère les entités de notification pour l'utilisateur
        List<NotificationEntity> entities = notificationDAO.findByUserId(userId);

        // Convertit les entités en DTOs
        return entities.stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }



}
