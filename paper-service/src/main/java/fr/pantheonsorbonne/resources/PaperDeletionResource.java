package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.resources.interfaces.DeletionResourceInterface;
import fr.pantheonsorbonne.service.PaperDeletionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

public class PaperDeletionResource implements DeletionResourceInterface {
    @Inject
    PaperDeletionService paperDeletionService;

    @DELETE
    @Path("/delete/{articleId}")
    public Response deletePaper(@PathParam("articleId") Long id) {
        try {
            this.paperDeletionService.deletePaper(id);
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
