package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.OpinionDTO;
import jakarta.ws.rs.core.Response;

public interface OpinionResourceInterface {

    Response addOpinion(OpinionDTO opinionDTO);
}
