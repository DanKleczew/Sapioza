package fr.pantheonsorbonne.resource;

import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.service.StoredPaperService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/stored-papers")
@Produces("application/json")
@Consumes("application/json")
public class StoredPaperResource {

    @Inject
    StoredPaperService storedPaperService;

    @POST
    public Response createStoredPaper(StoredPaperInputDTO dto) {
        try {
            storedPaperService.createStoredPaper(dto);
            return Response.status(Response.Status.CREATED).build();
        } catch (PaperDatabaseAccessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create StoredPaper: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{paperUuid}")
    public Response getStoredPaper(@PathParam("paperUuid") String id) {
        try {
            StoredPaperOutputDTO outputDTO = storedPaperService.getStoredPaper(id);
            return Response.ok(outputDTO).build();
        } catch (PaperNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("StoredPaper not found: " + e.getMessage()).build();
        }
    }
}
