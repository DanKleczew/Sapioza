package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.ReviewAlreadyExistsException;
import fr.pantheonsorbonne.exception.ReviewNotFoundException;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.model.Review;
import fr.pantheonsorbonne.service.PaperQueryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class ReviewDAO {

    @PersistenceContext
    private EntityManager em;

    @Inject
    PaperQueryDAO paperQueryDAO;

    public void addReview(Review review) throws ReviewAlreadyExistsException {
        try {
            Long reviewId = this.getReviewId(review.getPaper().getId(), review.getCommentAuthorId());
            if (reviewId != null) {
                throw new ReviewAlreadyExistsException(review.getPaper().getId(), review.getCommentAuthorId());
            }
            this.em.persist(review);
        } catch (RuntimeException e) {
            throw new PaperDatabaseAccessException();
        }
    }

    public List<Review> getReviews(Long articleId) throws PaperNotFoundException {
        try {
            Paper paper = this.paperQueryDAO.getPaper(articleId);
            if (paper == null) {
                throw new PaperNotFoundException(articleId);
            }
            return paper.getReviews();

        } catch (RuntimeException e) {
            throw new PaperDatabaseAccessException();
        }
    }

    public void removeReview(Long paperId, Long reviewerId) throws ReviewNotFoundException {
        try {
            Long reviewId = this.getReviewId(paperId, reviewerId);
            if (reviewId == null) {
                throw new ReviewNotFoundException(paperId, reviewerId);
            }
            Review review = this.em.find(Review.class, reviewId);
            this.em.remove(review);
        } catch (RuntimeException e){
            throw new PaperDatabaseAccessException();
        }
    }

    private Long getReviewId(Long paperId, Long reviewerId) {
        Long reviewId = null;
        reviewId = (Long) this.em
                .createQuery("SELECT r.id FROM Review r WHERE r.paper.id = :paperId AND r.commentAuthorId = :reviewerId")
                .setParameter("paperId", paperId)
                .setParameter("reviewerId", reviewerId)
                .getSingleResult();
        return reviewId;
    }
}
