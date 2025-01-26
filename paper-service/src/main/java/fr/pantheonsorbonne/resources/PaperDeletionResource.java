package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.PaperDTOs.DeletionPaperDTO;
import fr.pantheonsorbonne.enums.Cause;
import fr.pantheonsorbonne.global.UserIdentificationDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperOwnershipDeniedException;
import fr.pantheonsorbonne.resources.interfaces.DeletionResourceInterface;
import fr.pantheonsorbonne.service.PaperDeletionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/papers")
public class PaperDeletionResource implements DeletionResourceInterface {
    @Inject
    PaperDeletionService paperDeletionService;

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response deletePaper(DeletionPaperDTO deletionPaperDTO) {
        try {
            this.paperDeletionService.deletePaper(deletionPaperDTO.articleId(), deletionPaperDTO.userIdentificationDTO());
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } catch (PaperNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (PaperOwnershipDeniedException e) {
            if (e.getReason() == Cause.UNAUTHORIZED) {
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(e.getMessage())
                        .build();
            } else {
                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(e.getMessage())
                        .build();
            }
        }
        catch (PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
