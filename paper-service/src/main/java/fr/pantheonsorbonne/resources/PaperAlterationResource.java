package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.PaperDTOs.AlteredPaperDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperOwnershipDeniedException;
import fr.pantheonsorbonne.service.PaperAlterationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/paper")
public class PaperAlterationResource {

    @Inject
    PaperAlterationService paperAlterationService;

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alter")
    public Response alterPaper(AlteredPaperDTO alteredPaperDTO) {
        try {
            paperAlterationService.alterPaper(alteredPaperDTO);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (PaperNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (PaperOwnershipDeniedException e) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(e.getMessage())
                    .build();
        }
        catch (InternalCommunicationException | PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
