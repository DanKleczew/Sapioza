package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.OpinionDTO;
import fr.pantheonsorbonne.resources.interfaces.OpinionResourceInterface;
import fr.pantheonsorbonne.service.OpinionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/review")
public class OpinionResource implements OpinionResourceInterface {

    @Inject
    OpinionService opinionService;

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response addOpinion(OpinionDTO opinionDTO) {
        try {
            // TODO
            this.opinionService.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }
}
