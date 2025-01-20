package fr.pantheonsorbonne.ressource;

import fr.pantheonsorbonne.camel.gateway.UserNotificationGateway;
import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.dto.NotificationDTO;
import fr.pantheonsorbonne.service.NotificationCreationService;
import fr.pantheonsorbonne.service.NotificationQueryService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notifications")
public class NotificationResource {

    @Inject
    NotificationCreationService notificationCreationService;

    @Inject
    NotificationQueryService notificationQueryService;

    @Inject
    NotificationDAO notificationDAO;

    @Inject
    UserNotificationGateway userNotificationGateway;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public Response getNotificationsForUser(@PathParam("userId") Long userId) {
        try {
            List<NotificationDTO> notifications = notificationQueryService.getNotificationsForUser(userId);

            if (notifications.isEmpty()) {
                // Retourne 204 No Content si aucune notification n'est trouvée
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            // Retourne 200 OK avec les notifications si elles existent
            return Response.ok(notifications).build();
        } catch (IllegalArgumentException e) {
            // Retourne 400 Bad Request pour un userId invalide
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            // Retourne 500 Internal Server Error pour toute autre erreur
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving notifications.")
                    .build();
        }
    }


    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTestNotification() {
        try {
            //notificationDAO.create();
            return Response.ok("Test notification created successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create test notification: " + e.getMessage())
                    .build();
        }
    }


}


