package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.ReviewDAO;
import fr.pantheonsorbonne.dto.ReviewDTO;
import fr.pantheonsorbonne.exception.*;
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

    public void addReview(ReviewDTO reviewDTO) throws ReviewAlreadyExistsException {
        this.reviewDAO.addReview(this.reviewMapper.mapDTOToEntity(reviewDTO));
    }

    public List<ReviewDTO> getAllReviews(Long articleId) throws PaperNotFoundException {
        return this.reviewDAO.getReviews(articleId)
                .stream()
                .map(reviewMapper::mapEntityToDTO)
                .toList();
    }

    public void removeReview(Long paperId, Long reviewerId ) throws ReviewNotFoundException {
        this.reviewDAO.removeReview(paperId, reviewerId);
    }

}
