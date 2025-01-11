package fr.pantheonsorbonne.ressource;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.entity.NotificationEntity;
import fr.pantheonsorbonne.service.NotificationCreationService;
import fr.pantheonsorbonne.service.NotificationQueryService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationRessource {

    @Inject
    NotificationCreationService notificationCreationService;

    @Inject
    NotificationQueryService notificationQueryService;

    /**
     * Récupère les notifications pour un utilisateur donné.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une réponse HTTP contenant les notifications ou une erreur
     */
    @GET
    @Path("/{userId}")
    public Response getNotificationsForUser(@PathParam("userId") Long userId) {
        List<NotificationDTO> notifications = notificationQueryService.getNotificationsForUser(userId);

        if (notifications.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No notifications found for user ID: " + userId)
                    .build();
        }

        return Response.ok(notifications).build();
    }

    /**
     * Crée une nouvelle notification.
     *
     * @param notification les données de la notification
     * @return une réponse HTTP avec le statut de la création
     */
    @POST
    @Transactional
    public Response createNotification(NotificationEntity notification) {
        if (notification.getAuthorName() == null || notification.getPaperTitle() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Author name and paper title are required.")
                    .build();
        }

        // Créer la notification
        notificationCreationService.createNotification(notification.getAuthorName(), notification.getPaperTitle());

        return Response.status(Response.Status.CREATED)
                .entity("Notification created successfully.")
                .build();
    }
}
