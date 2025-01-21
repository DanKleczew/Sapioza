package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.OpinionDTO;
import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.model.Opinion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OpinionMapper implements EntityDTOMapper<OpinionDTO, Opinion> {

    @Inject
    PaperQueryDAO paperQueryDAO;

    @Override
    public OpinionDTO mapEntityToDTO(Opinion entity) {
        return new OpinionDTO(
                entity.getPaper().getId(),
                entity.getOpinion(),
                entity.getUserId());
    }

    @Override
    public Opinion mapDTOToEntity(OpinionDTO dto) {
        Opinion entity = new Opinion();
        entity.setPaper(this.paperQueryDAO.getPaper(dto.paperId()));
        entity.setUserId(dto.readerId());
        entity.setOpinion(dto.opinion());
        return entity;
    }
}
