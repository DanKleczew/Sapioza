package fr.pantheonsorbonne.resources;

//import fr.pantheonsorbonne.exception.PaperNotFoundException;
//import fr.pantheonsorbonne.service.PaperService;
import fr.pantheonsorbonne.dao.UserDAO;
import fr.pantheonsorbonne.entity.User;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/User")
public class UserAPI {

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/{id}")
        public Response getUserInfo(@PathParam("id") long id) {
                UserDAO userDAO = new UserDAO();
                //User user = userDAO.getUser(id);
                //User user = new User();
                //user.setEmail("test2");
                //user.setName("test2");
                //user.setFirstName("test2");
                //user.setPassword("test2");
                //user.setId((long) 12);
                //userDAO.addUser(user);
                Log.debug("UserAPI.getUserInfo called with id=" + id);
                //System.out.println("qsdqsdsqd"+user.toString());
                return Response.ok().build();
        }

}
