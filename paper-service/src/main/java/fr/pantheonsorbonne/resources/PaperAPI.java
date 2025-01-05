package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.Filter;
import fr.pantheonsorbonne.entity.Paper;
import fr.pantheonsorbonne.enums.ResearchField;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.service.PaperService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public List<Paper> getFilteredPapers(@QueryParam("title") String title,
                                         @QueryParam("author") Long authorId,
                                         @QueryParam("abstract") String abstract_,
                                         @QueryParam("keywords") String keywords,
                                         @QueryParam("revue") String revue,
                                         @QueryParam("field") String field) {
        return this.paperService.getFilteredPapers(new Filter(title, authorId ,abstract_, keywords, revue, ResearchField.valueOf(field)));
    }

}
