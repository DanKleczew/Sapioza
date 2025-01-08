package fr.pantheonsorbonne.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.pantheonsorbonne.dto.CompletePaperDTO;
import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.dto.PaperDTO;
import fr.pantheonsorbonne.dto.PaperMetaDataDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotCreatedException;
import fr.pantheonsorbonne.model.Paper;
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
    public Response getPaperInfos(@PathParam("id") Long id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(this.paperService.getPaperInfos(id))
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
    public List<PaperDTO> getFilteredPapers(@QueryParam("title") String title,
                                            @QueryParam("author") Long authorId,
                                            @QueryParam("abstract") String abstract_,
                                            @QueryParam("keywords") String keywords,
                                            @QueryParam("revue") String revue,
                                            @QueryParam("field") String field) {
        return this.paperService.getFilteredPapers(new FilterDTO(title,
                authorId ,abstract_, keywords, revue, ResearchField.valueOf(field)));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/submit")
    public Response createPaper(CompletePaperDTO completePaperDTO) {
        try {
            PaperMetaDataDTO paperMetaDataDTO = this.paperService.createPaper(completePaperDTO);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(paperMetaDataDTO)
                    .build();
        } catch (BadRequestException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Malformed JSON")
                    .build();
        } catch (PaperNotCreatedException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        } catch (PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{articleId}")
    public Response deletePaper(@PathParam("articleId") Long id) {
        try {
            this.paperService.deletePaper(id);
            return Response
                    .status(Response.Status.NO_CONTENT)
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

}
