package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.CompletePaperDTO;
import jakarta.ws.rs.core.Response;

public interface CreationResourceInterface {

    Response createPaper(CompletePaperDTO cpd);
}
