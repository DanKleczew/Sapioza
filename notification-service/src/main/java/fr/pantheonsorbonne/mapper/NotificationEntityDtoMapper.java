package fr.pantheonsorbonne.mapper;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.model.Notification;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationEntityDtoMapper implements EntityDTOMapper<NotificationDTO, Notification> {

    @Override
    public NotificationDTO mapEntityToDTO(Notification entity) {
        if (entity == null) {
            return null;
        }
        return new NotificationDTO(
                entity.getNotifiedUserId(),
                entity.getPaperId(),
                entity.getAuthorFirstName(),
                entity.getAuthorLastName(),
                entity.getPaperTitle(),
                entity.getNotificationTime());
    }

    @Override
    public Notification mapDTOToEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Notification(
                dto.notifiedUserId(),
                dto.paperId(),
                dto.authorFirstName(),
                dto.authorLastName(),
                dto.paperTitle(),
                dto.dateNotification());
    }

}
