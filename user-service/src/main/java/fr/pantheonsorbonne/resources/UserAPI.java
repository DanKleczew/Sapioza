package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.exception.User.UserException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
import fr.pantheonsorbonne.service.UserService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/User")
public class UserAPI {

        @Inject
        UserService userService;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/getInfo/{id}")
        public Response getUserInfo(@PathParam("id") Long id) {
                try {
                        UserDTO userDTO = userService.getUser(id);
                        return Response
                                .ok(userDTO)
                                .build();
                }
                catch (UserNotFoundException e) {
                        return Response
                                .status(Response.Status.NOT_FOUND)
                                .build();
                }
        }

        @PATCH
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/subscribe/{idAuthor}/{idSubscriber}")
        public Response subscribeTo(
                @PathParam("idAuthor") Long id1,
                @PathParam("idSubscriber") Long id2
        ) {
                try {
                        userService.subscribeTo(id1, id2);
                        UserDTO userDTO = userService.getUser(id1);
                        return Response
                                .ok(userDTO)
                                .build();
                }
                catch (UserNotFoundException e) {
                        return Response
                                .status(Response.Status.NOT_FOUND)
                                .build();
                }
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("getSubscribers/{id}")
        public Response getSubscribers(
                @PathParam("id") long id
        ) {
                List<Long> usersId = userService.getSubscribersId(id);
                Log.debug("UserAPI.getSubscribers called with id=" + id);
                return Response.ok(usersId).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("getSubscribes/{id}")
        public Response getSubscribs(
                @PathParam("id") long id
        ) {
                List<Long> usersId = userService.findUserFollowsID(id);
                Log.debug("UserAPI.getSubscribs called with id=" + id);
                return Response.ok(usersId).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/connection/{email}/{password}")
        public Response connectionUser(
                @PathParam("email") String email,
                @PathParam("password") String password
        ){
                try {
                        UserDTO userDTO = userService.connectionUser(email, password);
                        return Response.ok(userDTO).build();
                }
                catch (UserNotFoundException e) {
                        return Response
                                .status(Response.Status.NOT_FOUND)
                                .build();
                } catch (UserException e) {
                        return Response
                                .status(Response.Status.UNAUTHORIZED)
                                .build();
                }

        }
}
