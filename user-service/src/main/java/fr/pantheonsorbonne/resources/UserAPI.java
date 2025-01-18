package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.dto.UserRegistrationDTO;
import fr.pantheonsorbonne.exception.Connection.ConnectionException;
import fr.pantheonsorbonne.exception.User.UserNotFoundException;
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
        @Path("/createAccount/{name}/{firstName}/{email}/{password}")
        public Response createAccount(
                @PathParam("name") String name,
                @PathParam("firstName") String firstName,
                @PathParam("email") String email,
                @PathParam("password") String password
        ) {
                if(userService.getUser(email) != null) {
                        return Response.status(Response.Status.CONFLICT).build();
                }
                userService.createUser(new UserRegistrationDTO(name, firstName, email, password));
                UserDTO userDTO = userService.getUser(email);
                Log.debug("UserAPI.createAccount called with name=" + name + " firstName=" + firstName + " email=" + email + " password=" + password);
                return Response.ok(userDTO).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/getInfo/{id}")
        public Response getUserInfo(@PathParam("id") long id) {
                UserDTO userDTO = userService.getUser(id);
                Log.debug("UserAPI.getUserInfo called with id=" + id);
                return Response.ok(userDTO).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/delete/{id},{password}")
        public Response deleteUser(@PathParam("id") long id, @PathParam("password") String password) {
                if(userService.getUser(id) == null){
                        return Response.status(Response.Status.NOT_FOUND).build();
                }
                UserDTO userDTO = userService.getUser(id);
                userService.deleteUser(id, password);
                Log.debug("UserAPI.deleteUser called with id=" + id);
                return Response.ok(userDTO).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/subscribe/{id1}/{id2}")
        public Response subscribeTo(
                @PathParam("id1") long id1,
                @PathParam("id2") long id2
        ) {

                userService.subscribTo(id1, id2);
                UserDTO userDTO = userService.getUser(id1);
                Log.debug("UserAPI.createTestUser called");
                return Response.ok(userDTO).build();
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
        ) throws ConnectionException {
                UserDTO userDTO = userService.connectionUser(email, password);
                Log.debug("UserAPI.connectionUser called with email=" + email + " password=" + password);
                return Response.ok(userDTO).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/getUserByUUID/{uuid}")
        public Response getUserByUUID(@PathParam("uuid") String uuid) throws UserNotFoundException {
                UserDTO userDTO = userService.getUserByUuid(uuid);
                Log.debug("UserAPI.getUserByUUID called with uuid=" + uuid);
                return Response.ok(userDTO).build();
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
                List<Long> users = userService.findUserFollowersID(id);
                Log.debug("UserAPI.getSubscribers called with id=" + id);
                return Response.ok(users).build();
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
