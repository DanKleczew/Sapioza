package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.OpinionDAO;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.OpinionDTO;
import fr.pantheonsorbonne.exception.OpinionNotFoundException;
import fr.pantheonsorbonne.mappers.OpinionMapper;
import fr.pantheonsorbonne.model.Opinion;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class OpinionService {
    @Inject
    OpinionMapper opinionMapper;

    @Inject
    OpinionDAO opinionDAO;

    @Inject
    PaperQueryDAO paperQueryDAO;

    public void changeOpinion(OpinionDTO opinionDto) throws OpinionNotFoundException {
        Opinion op = this.opinionMapper.mapDTOToEntity(opinionDto);
        if (op.getOpinion() == null) {
            this.opinionDAO.removeOpinion(op);
        } else {
            this.opinionDAO.changeOpinion(op);
        }
    }

    public List<OpinionDTO> getOpinions(Long paperId) throws OpinionNotFoundException {
        Paper paper = this.paperQueryDAO.getPaper(paperId);
        List<Opinion> opinions = this.opinionDAO.getAllOpinions(paper);

        if (opinions.isEmpty()) {
            throw new OpinionNotFoundException(paperId);
        }
        return opinions.stream().map(opinionMapper::mapEntityToDTO).toList();
    }

    public OpinionDTO getOpinion(Long paperId, Long userId) throws OpinionNotFoundException {
        Opinion op = new Opinion();
        op.setPaper(this.paperQueryDAO.getPaper(paperId));
        op.setUserId(userId);

        return opinionMapper.mapEntityToDTO(this.opinionDAO.findOpinion(op));
    }

}
