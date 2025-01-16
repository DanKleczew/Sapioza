package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.OpinionDTO;
import jakarta.ws.rs.core.Response;

public interface OpinionResourceInterface {

    Response changeOpinion(OpinionDTO opinionDTO);

    Response getOpinion(Long paperId, Long userId);

    Response getAllOpinions(Long paperId);
}
