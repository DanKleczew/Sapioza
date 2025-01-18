package fr.pantheonsorbonne.ressource;

import fr.pantheonsorbonne.dao.NotificationDAO;
import fr.pantheonsorbonne.dto.NotificationDTO;
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
public class NotificationResource {

    @Inject
    NotificationCreationService notificationCreationService;

    @Inject
    NotificationQueryService notificationQueryService;

    @Inject
    NotificationDAO notificationDAO;

    /*
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
    }*/

    @GET
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
                    .entity("Invalid user ID: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            // Retourne 500 Internal Server Error pour toute autre erreur
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving notifications.")
                    .build();
        }
    }



    //from new paper p2n recupère info avec le format et je gère => pas de create ici
    /*@POST
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
    }*/

    /*@GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTestNotification () {
        this.notificationDAO.createTestNotification();
        return Response.ok().build();
    }*/

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNotification(NotificationDTO dto) {
        try {
            // Validation simple (si nécessaire)
            if (dto.getAuthorName() == null || dto.getPaperTitle() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Author name and paper title are required.")
                        .build();
            }

            // Appeler le service pour la logique métier
            notificationCreationService.createNotification(dto);

            return Response.status(Response.Status.CREATED)
                    .entity("Notification created successfully.")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create notification: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTestNotification() {
        try {
            notificationDAO.createTestNotification();
            return Response.ok("Test notification created successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create test notification: " + e.getMessage())
                    .build();
        }
    }
}
