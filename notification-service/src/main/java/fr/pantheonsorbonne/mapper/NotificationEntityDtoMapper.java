package fr.pantheonsorbonne.mapper;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.model.Notification;

import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Date;

@ApplicationScoped
public class NotificationEntityDtoMapper implements EntityDTOMapper<NotificationDTO, Notification> {

    @Override
    public NotificationDTO mapEntityToDTO(Notification entity) {
        if (entity == null) {
            return null;
        }
        return new NotificationDTO(entity.getNotifiedUserId(),
                entity.getPaperId(),
                entity.getAuthorId(),
                entity.getPaperTitle(),
                entity.getNotificationTime());
    }

    @Override
    public Notification mapDTOToEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Notification(dto.notifiedUserId(),
                dto.paperId(),
               dto.authorId(),
                dto.paperTitle(),
                dto.dateNotification());
    }

}
