package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dao.OpinionDAO;
import fr.pantheonsorbonne.dto.PaperDTO;
import fr.pantheonsorbonne.dto.PaperOpinionsDTO;
import fr.pantheonsorbonne.dto.ReviewDTO;
import fr.pantheonsorbonne.exception.OpinionNotFoundException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.model.Review;
import fr.pantheonsorbonne.service.OpinionService;
import fr.pantheonsorbonne.service.ReviewService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PaperMapper implements EntityDTOMapper<PaperDTO, Paper> {

    @Inject
    OpinionService opinionService;

    @Inject
    ReviewService reviewService;

    @Override
    public PaperDTO mapEntityToDTO(Paper entity) {
        PaperOpinionsDTO paperOpinionsDTO = null;
        try {
            paperOpinionsDTO = this.opinionService.getOpinions(entity.getId());
        } catch (OpinionNotFoundException e) {
            paperOpinionsDTO = new PaperOpinionsDTO(0, 0);
        }
        List<ReviewDTO> reviews = null;
        try {
            reviews = this.reviewService.getAllReviews(entity.getId());
        } catch (PaperNotFoundException e) {
            reviews = List.of();
        }

        return new PaperDTO(
                entity.getTitle(),
                entity.getAuthorId(),
                entity.getField(),
                entity.getPublishedIn(),
                entity.getPublicationDate(),
                entity.getKeywords(),
                entity.getAbstract_(),
                entity.getDOI(),
                paperOpinionsDTO.likes(),
                paperOpinionsDTO.dislikes(),
                reviews.stream().map(ReviewDTO::comment).collect(Collectors.toList()));
    }

    @Override
    public Paper mapDTOToEntity(PaperDTO dto) {
        Paper paper = new Paper();
        paper.setTitle(dto.title());
        paper.setAuthorId(dto.authorId());
        paper.setField(dto.field());
        paper.setPublishedIn(dto.publishedIn());
        paper.setPublicationDate(dto.publicationDate());
        paper.setKeywords(dto.keywords());
        paper.setAbstract_(dto.abstract_());
        paper.setDOI(dto.DOI());
        return paper;
    }

    public PaperMetaDataDTO mapPaperToPaperMetaDataDTO(Paper paper) {
        return new PaperMetaDataDTO(
                paper.getId(),
                paper.getTitle(),
                paper.getAuthorId(),
                paper.getPublicationDate()
        );
    }
}
