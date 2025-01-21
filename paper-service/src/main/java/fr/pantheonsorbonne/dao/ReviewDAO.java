package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.ReviewAlreadyExistsException;
import fr.pantheonsorbonne.exception.ReviewNotFoundException;
import fr.pantheonsorbonne.model.Review;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class ReviewDAO {

    @PersistenceContext
    private EntityManager em;

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

    public List<Review> getReviews(Long articleId)  {
            return this.em.createQuery("SELECT r FROM Review r WHERE r.paper.id = :articleId", Review.class)
                    .setParameter("articleId", articleId)
                    .getResultList();

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
        try {
        return (Long) this.em
                .createQuery("SELECT r.id FROM Review r WHERE r.paper.id = :paperId AND r.commentAuthorId = :reviewerId")
                .setParameter("paperId", paperId)
                .setParameter("reviewerId", reviewerId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
