package fr.pantheonsorbonne.resources;

import fr.pantheonsorbonne.dto.ReviewDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.ReviewNotCreatedException;
import fr.pantheonsorbonne.exception.ReviewNotFoundException;
import fr.pantheonsorbonne.resources.interfaces.ReviewResourceInterface;
import fr.pantheonsorbonne.service.ReviewService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/review")
public class ReviewResource implements ReviewResourceInterface {

    @Inject
    ReviewService reviewService;

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public Response addComment(ReviewDTO reviewDTO) {
        try {
            this.reviewService.addReview(reviewDTO);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (BadRequestException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        catch (ReviewNotCreatedException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Override
    @Path("/remove/{paperId}/{reviewerId}")
    public Response removeComment(@QueryParam("paperId") Long paperId , @QueryParam("reviewerId") Long reviewerId) {
        try {
            this.reviewService.removeReview(paperId, reviewerId);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
        catch (ReviewNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        catch (PaperDatabaseAccessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
