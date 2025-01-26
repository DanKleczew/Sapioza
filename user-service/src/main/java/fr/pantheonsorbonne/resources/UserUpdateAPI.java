package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.UserFirstNameUpdate;
import fr.pantheonsorbonne.dto.UserNameUpdate;
import fr.pantheonsorbonne.dto.UserPasswordUpdate;
import fr.pantheonsorbonne.exception.User.UserAuthenticationException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.service.UserDeleteService;
import fr.pantheonsorbonne.service.UserUpdateService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user/update")
public class UserUpdateAPI {

    @Inject
    UserDeleteService userDeleteService;

    @Inject
    UserUpdateService userUpdateService;

    @POST
    @Path("/name")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateName(UserNameUpdate userNameUpdate) {
        try {
            userUpdateService.updateName(userNameUpdate);
            return Response
                    .ok("User name has been updated")
                    .build();
        }
        catch (UserNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (UserAuthenticationException e) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .build();
        }
    }

    @POST
    @Path("/firstName")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFirstName(UserFirstNameUpdate userFirstNameUpdate) {
        try {
            userUpdateService.updateFirstName(userFirstNameUpdate);
            return Response
                    .ok("User first name has been updated")
                    .build();
        }
        catch (UserNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (UserAuthenticationException e) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .build();
        }
    }

    @POST
    @Path("/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(UserPasswordUpdate userPasswordUpdate) {
        try {
            userUpdateService.updatePassword(userPasswordUpdate);
            return Response
                    .ok("User password has been updated")
                    .build();
        }
        catch (UserNotFoundException e) {
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
