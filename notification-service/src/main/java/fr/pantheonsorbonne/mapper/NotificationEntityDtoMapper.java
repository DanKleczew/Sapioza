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

    /*public Notification mapToEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        Notification entity = new Notification();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setPaperId(dto.getPaperId());
        entity.setAuthorName(dto.getAuthorName());
        entity.setPaperTitle(dto.getPaperTitle());
        entity.setViewed(dto.isViewed());
        return entity;
    }*/
}
