package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.PaperDTOs.AlteredPaperDTO;
import jakarta.ws.rs.core.Response;

public interface AlterationResourceInterface {

    public Response alterPaper(AlteredPaperDTO alteredPaperDTO);
}
