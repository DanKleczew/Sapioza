package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.ReviewDTO;
import jakarta.ws.rs.core.Response;

public interface ReviewResourceInterface {

    Response addComment(ReviewDTO reviewDTO);
    // Should be allowed only if there is no other comment by the commentAuthor on this Paper

    Response removeComment(Long paperId, Long reviewerId);
}
