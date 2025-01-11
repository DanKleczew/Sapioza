package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.entity.NotificationEntity;
import fr.pantheonsorbonne.mapper.NotificationEntityDtoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationQueryService {

    @Inject
    NotificationDAO notificationDAO;

    @Inject
    NotificationEntityDtoMapper mapper;

    /**
     * Récupère les notifications pour un utilisateur donné.
     *
     * @param userId L'identifiant de l'utilisateur.
     * @return Une liste de NotificationDTO.
     */
    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        List<NotificationEntity> entities = notificationDAO.findByUserId(userId);
        return entities.stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère toutes les notifications pour un utilisateur sous forme brute.
     *
     * @param userId L'identifiant de l'utilisateur.
     * @return Une liste de NotificationEntity.
     */
    public List<NotificationEntity> getNotificationsByUserId(Long userId) {
        return notificationDAO.findByUserId(userId);
    }
}
