package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import jakarta.ws.rs.core.Response;

public interface CreationResourceInterface {

    Response createPaper(SubmittedPaperDTO cpd);
}
