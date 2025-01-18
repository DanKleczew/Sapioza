package fr.pantheonsorbonne.mapper;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.model.Notification;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationEntityDtoMapper {

    public NotificationDTO mapToDTO(Notification entity) {
        if (entity == null) {
            return null;
        }
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(entity.getUserId());
        dto.setPaperId(entity.getPaperId());
        dto.setAuthorName(entity.getAuthorName());
        dto.setPaperTitle(entity.getPaperTitle());
        dto.setViewed(entity.isViewed());
        return dto;
    }
}
