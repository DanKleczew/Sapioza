package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.model.Notification;
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

    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        List<Notification> entities = notificationDAO.findByUserId(userId);
        return entities.stream()
                .map(mapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationDAO.findByUserId(userId);
    }

}
