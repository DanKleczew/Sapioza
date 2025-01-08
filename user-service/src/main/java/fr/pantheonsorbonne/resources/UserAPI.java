package fr.pantheonsorbonne.resources;

//import fr.pantheonsorbonne.exception.PaperNotFoundException;
//import fr.pantheonsorbonne.service.PaperService;
import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.entity.User;
import fr.pantheonsorbonne.service.UserService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

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
        @Path("/subscribers/{id}")
        public Response getSubscribers(@PathParam("id") long id) {
                List<User> users = userService.getSubscribers(id);
                for (User user : users) {
                        System.out.println(user.toString() + " HERE \n" );
                }
                Log.debug("UserAPI.getSubscribers called with id=" + id);
                return Response.ok().build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/createAccount/{name}/{firstName}/{email}/{password}")
        public Response createAccount(@PathParam("name") String name, @PathParam("firstName") String firstName, @PathParam("email") String email, @PathParam("password") String password) {
                userService.createAccount(name, firstName, email, password);
                Log.debug("UserAPI.createAccount called with name=" + name + " firstName=" + firstName + " email=" + email + " password=" + password);
                return Response.ok().build();
        }

}
