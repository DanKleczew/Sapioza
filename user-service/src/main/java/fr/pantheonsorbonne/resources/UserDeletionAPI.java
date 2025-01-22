package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.UserDeletionDTO;
import fr.pantheonsorbonne.exception.User.UserAuthenticationException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.service.UserCreationService;
import fr.pantheonsorbonne.service.UserDeleteService;
import fr.pantheonsorbonne.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user/delete")
public class UserDeletionAPI {

    @Inject
    UserService userService;

    @Inject
    UserDeleteService userDeleteService;

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response deleteUser(UserDeletionDTO userDeletionDTO) {
        try {
            userDeleteService.deleteUser(userDeletionDTO);
            return Response
                    .ok("User account " + userDeletionDTO.id() + " has been deleted")
                    .build();
        } catch (UserNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (UserAuthenticationException e) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .build();
        }
    }
}
