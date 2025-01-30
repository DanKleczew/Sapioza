package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.User.UserAlreadyExistsException;
import fr.pantheonsorbonne.service.UserCreationService;
import fr.pantheonsorbonne.service.UserDeleteService;
import fr.pantheonsorbonne.service.UserService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user/create")
public class UserCreationAPI {

    @Inject
    UserCreationService userCreationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createAccount")
    public Response createAccount(UserRegistrationDTO userRegistrationDTO) {
        try {
            userCreationService.createUser(userRegistrationDTO);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(userRegistrationDTO.email())
                    .build();
        } catch (UserAlreadyExistsException e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(e.getMessage())
                    .build();
        }catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
