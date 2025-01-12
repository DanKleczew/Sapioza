package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.ReviewDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.model.Review;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReviewMapper implements EntityDTOMapper<ReviewDTO, Review> {

    @Inject
    PaperQueryDAO paperQueryDAO;

    @Override
    public ReviewDTO mapEntityToDTO(Review entity) {
        return new ReviewDTO(
                entity.getPaper().getId(),
                entity.getCommentAuthorId(),
                entity.getComment());
    }

    @Override
    public Review mapDTOToEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setPaper(this.paperQueryDAO.getPaper(dto.paperId()));
        review.setCommentAuthorId(dto.authorId());
        review.setComment(dto.comment());
        return review;
    }
}
