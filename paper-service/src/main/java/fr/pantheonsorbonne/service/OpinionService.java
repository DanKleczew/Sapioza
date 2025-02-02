package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.OpinionDAO;
import fr.pantheonsorbonne.dao.PaperQueryDAO;
import fr.pantheonsorbonne.dto.OpinionDTO;
import fr.pantheonsorbonne.dto.PaperOpinionsDTO;
import fr.pantheonsorbonne.enums.OpinionList;
import fr.pantheonsorbonne.exception.OpinionNotFoundException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.mappers.OpinionMapper;
import fr.pantheonsorbonne.model.Opinion;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
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

    public void changeOpinion(OpinionDTO opinionDto) throws OpinionNotFoundException, PaperNotFoundException {
        if (this.paperQueryDAO.getPaper(opinionDto.paperId()) == null){
            throw new PaperNotFoundException(opinionDto.paperId());
        }
        Opinion op = this.opinionMapper.mapDTOToEntity(opinionDto);

        if (op.getOpinion() == OpinionList.NO_OPINION) {
            this.opinionDAO.removeOpinion(op);
        } else {
            this.opinionDAO.changeOpinion(op);
        }
    }

    public PaperOpinionsDTO getOpinions(Long paperId) throws OpinionNotFoundException {
        Paper paper = this.paperQueryDAO.getPaper(paperId);
        List<Opinion> opinions = this.opinionDAO.getAllOpinions(paper);

        if (opinions.isEmpty()) {
            throw new OpinionNotFoundException(paperId);
        }
        int likes = 0;
        int dislikes = 0;
        for (Opinion opinion : opinions) {
            if (opinion.getOpinion() == OpinionList.LIKE) {
                likes++;
            } else {
                dislikes++;
            }
        }
        return new PaperOpinionsDTO(likes, dislikes);
    }

    public OpinionDTO getOpinion(Long paperId, Long userId) throws OpinionNotFoundException {
        Opinion op = new Opinion();
        Paper paper = this.paperQueryDAO.getPaper(paperId);
        if (paper == null) {
            throw new OpinionNotFoundException(paperId);
        }
        op.setPaper(paper);
        op.setUserId(userId);

        return opinionMapper.mapEntityToDTO(this.opinionDAO.findOpinion(op));
    }

}
