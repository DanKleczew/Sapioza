package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.service.PaperService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/papers")
public class PaperAPI {

    @Inject
    PaperService paperService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPaperInfos(@PathParam("id") int id) {
        try {
            return Response.ok(this.paperService.getPaperInfos(id)).build();
        } catch (PaperNotFoundException e) {
            return Response.status(404, e.getMessage()).build();
        }
    }

}
