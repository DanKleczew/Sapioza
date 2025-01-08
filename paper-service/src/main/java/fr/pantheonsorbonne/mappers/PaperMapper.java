package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.PaperDTO;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;

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
            entity.getAbstract_()
        );
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
        return paper;
    }
}