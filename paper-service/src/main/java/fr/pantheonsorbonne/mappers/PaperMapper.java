package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.PaperDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.model.Paper;
import fr.pantheonsorbonne.model.Review;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.stream.Collectors;

@ApplicationScoped
public class PaperMapper implements EntityDTOMapper<PaperDTO, Paper> {
    @Override
    public PaperDTO mapEntityToDTO(Paper entity) {
        return new PaperDTO(
                entity.getTitle(),
                entity.getAuthorId(),
                entity.getField(),
                entity.getPublishedIn(),
                entity.getPublicationDate(),
                entity.getKeywords(),
                entity.getAbstract_(),
                entity.getDOI(),
                entity.getOpinions().toArray().length,
                entity.getReviews()
                        .stream()
                        .map(Review::getComment)
                        .collect(Collectors.toList()));
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
