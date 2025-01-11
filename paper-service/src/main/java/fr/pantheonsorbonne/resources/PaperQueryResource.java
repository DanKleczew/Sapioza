package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.enums.ResearchField;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.resources.interfaces.QueryResourceInterface;
import fr.pantheonsorbonne.service.PaperQueryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class PaperQueryResource implements QueryResourceInterface {
    @Inject
    PaperQueryService paperQueryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPaperInfos(@PathParam("id") Long id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(this.paperQueryService.getPaperInfos(id))
                    .build();
        } catch (PaperNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public Response getFilteredPapers(@QueryParam("title") String title,
                                            @QueryParam("author") Long authorId,
                                            @QueryParam("abstract") String abstract_,
                                            @QueryParam("keywords") String keywords,
                                            @QueryParam("revue") String revue,
                                            @QueryParam("field") String field) {
        return Response.ok().entity(this.paperQueryService.getFilteredPapers(new FilterDTO(title,
                authorId, abstract_, keywords, revue, ResearchField.valueOf(field)))).build();
    }
}