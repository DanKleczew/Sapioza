package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.PaperDTOs.PaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotCreatedException;
import fr.pantheonsorbonne.resources.interfaces.CreationResourceInterface;
import fr.pantheonsorbonne.service.PaperCreationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/papers")
public class PaperCreationResource implements CreationResourceInterface {
    @Inject
    PaperCreationService paperCreationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/submit")
    public Response createPaper(SubmittedPaperDTO submittedPaperDTO) {
        try {
            PaperMetaDataDTO paperMetaDataDTO = this.paperCreationService.createPaper(submittedPaperDTO);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(paperMetaDataDTO)
                    .build();
        } catch (BadRequestException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (PaperNotCreatedException | PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
