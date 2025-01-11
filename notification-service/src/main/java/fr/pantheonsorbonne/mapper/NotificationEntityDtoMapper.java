package fr.pantheonsorbonne.mapper;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.entity.NotificationEntity;

public class NotificationEntityDtoMapper {

    public NotificationDTO mapToDTO(NotificationEntity entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setAuthorName(entity.getAuthorName());
        dto.setPaperTitle(entity.getPaperTitle());
        dto.setViewed(entity.isViewed());
        return dto;
    }

    public NotificationEntity mapToEntity(NotificationDTO dto) {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(dto.getId());
        entity.setAuthorName(dto.getAuthorName());
        entity.setPaperTitle(dto.getPaperTitle());
        entity.setViewed(dto.isViewed());
        return entity;
    }
}
