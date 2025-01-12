package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.ReviewDAO;
import fr.pantheonsorbonne.dto.ReviewDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.ReviewNotCreatedException;
import fr.pantheonsorbonne.exception.ReviewNotFoundException;
import fr.pantheonsorbonne.mappers.ReviewMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReviewService {

    @Inject
    ReviewDAO reviewDAO;

    @Inject
    ReviewMapper reviewMapper;

    public void addReview(ReviewDTO reviewDTO) throws ReviewNotCreatedException {
        try {
            this.reviewDAO.addReview(this.reviewMapper.mapDTOToEntity(reviewDTO));
        } catch (PaperDatabaseAccessException e) {
            throw new ReviewNotCreatedException();
        }
    }

    public List<ReviewDTO> getAllReviews(Long articleId) throws PaperNotFoundException {
        return this.reviewDAO.getReviews(articleId)
                .stream()
                .map(reviewMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public void removeReview(Long paperId, Long reviewerId ) throws ReviewNotFoundException {
        this.reviewDAO.removeReview(paperId, reviewerId);
    }

}
