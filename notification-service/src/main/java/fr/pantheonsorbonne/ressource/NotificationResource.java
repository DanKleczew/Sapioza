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
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.ok(notifications).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving notifications.")
                    .build();
        }
    }

}


