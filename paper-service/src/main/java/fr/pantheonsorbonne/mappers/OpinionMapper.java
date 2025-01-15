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
                entity.getOpinion() == Boolean.TRUE ? 1 : 2,
                entity.getUserId());
    }

    @Override
    public Opinion mapDTOToEntity(OpinionDTO dto) {
        Opinion entity = new Opinion();
        entity.setPaper(this.paperQueryDAO.getPaper(dto.paperId()));
        entity.setUserId(dto.readerId());
        switch (dto.opinion()){
            case 0:
                entity.setOpinion(null);
                break;
            case 1:
                entity.setOpinion(true);
                break;
            default:
                entity.setOpinion(false);
                break;
        }
        return entity;
    }
}
