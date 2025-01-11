

/*
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class NotificationRessource {

    @Inject
    NotificationService notificationService;

    @GET
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @POST
    public Response createNotification(Notification notification) {
        if (notification.getAuthorName() == null || notification.getPaperTitle() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Author name and paper title are required.")
                    .build();
        }
        notificationService.createNotification(notification.getAuthorName(), notification.getPaperTitle());
        return Response.status(Response.Status.CREATED)
                .entity("Notification created with ID: " + notification.getId())
                .build();
    }
}
*/

/*

deuxième essaie pour enlever kafka
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationRessource {

    @Inject
    NotificationService notificationService;

    @GET
    @Path("/{userId}")
    public Response getNotifications(@PathParam("userId") Long userId) {
        List<NotificationEntity> notifications = notificationService.getNotificationsByUserId(userId);
        if (notifications.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No notifications found for user ID: " + userId)
                    .build();
        }
        return Response.ok(notifications).build();
    }
}

*/

package fr.pantheonsorbonne.ressource;

import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.service.NotificationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationRessource {

    @Inject
    NotificationService notificationService;

    /**
     * Récupère les notifications pour un utilisateur donné.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une réponse HTTP contenant les notifications ou une erreur
     */
    @GET
    @Path("/{userId}")
    public Response getNotificationsForUser(@PathParam("userId") Long userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userId);

        if (notifications.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No notifications found for user ID: " + userId)
                    .build();
        }

        return Response.ok(notifications).build();
    }
}


//entitymanager.persist