package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.OpinionDTO;
import fr.pantheonsorbonne.exception.OpinionNotFoundException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.resources.interfaces.OpinionResourceInterface;
import fr.pantheonsorbonne.service.OpinionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/opinion")
public class OpinionResource implements OpinionResourceInterface {

    @Inject
    OpinionService opinionService;

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("change")
    public Response changeOpinion(OpinionDTO opinionDTO) {
        try {
            this.opinionService.changeOpinion(opinionDTO);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (OpinionNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    @GET
    @Path("getSingle/{paperId}/{userId}")
    public Response getOpinion(@PathParam("paperId") Long paperId,
                               @PathParam("userId") Long userId) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(this.opinionService.getOpinion(paperId, userId))
                    .build();
        } catch (OpinionNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (PaperDatabaseAccessException e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Override
    public Response getAllOpinions(Long paperId) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(this.opinionService.getOpinions(paperId))
                    .build();
        } catch (OpinionNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (PaperDatabaseAccessException e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
