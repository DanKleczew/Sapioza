package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.model.User;
import fr.pantheonsorbonne.service.UserService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static org.bouncycastle.oer.its.ieee1609dot2.CertificateId.name;

@Path("/User")
public class UserAPI {

        @Inject
        UserService userService;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/{id}")
        public Response getUserInfo(@PathParam("id") long id) {
                userService.getUserInfos(id);
                Log.debug("UserAPI.getUserInfo called with id=" + id);
                //System.out.println("qsdqsdsqd"+user.toString());
                return Response.ok().build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/subscribe/{id1}/{id2}")
        public Response subscribeTo(
                @PathParam("id1") long id1,
                @PathParam("id2") long id2
        ) {

                userService.subscribing(1, 2);
                Log.debug("UserAPI.createTestUser called");
                return Response.ok().build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/createTestUser")
        public Response createTestUser() {
                userService.createTestUser();
                Log.debug("UserAPI.createTestUser called");
                return Response.ok().build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/subscribers/{mail}")
        public Response getSubscribers(@PathParam("mail") String mail) {
                List<User> users = userService.getSubscribers(mail);
                for (User user : users) {
                        System.out.println(user.toString() + " HERE \n" );
                }
                Log.debug("UserAPI.getSubscribers called with id=" + mail);
                return Response.ok().build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/informations/{id}")
        public Response findUsersFollowedByNative(@PathParam("id") long id) {
                //List<Long> users = userService.findUserFollowersID(id);
                List<UserDTO> users = userService.findUserFollowersDTO(id);
                Log.debug("UserAPI.getSubscribers called with id=" + id);
                return Response.ok().build();
        }

        /*
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/createAccount")
        public Response createAccount(UserRegistrationDTO userRegistrationDTO) {

                userService.createAccount(userRegistrationDTO);
                Log.debug("UserAPI.createAccount called with name=" + name + " firstName=" + firstName + " email=" + email + " password=" + password);
                return Response.ok().build();
        }

         */

}
