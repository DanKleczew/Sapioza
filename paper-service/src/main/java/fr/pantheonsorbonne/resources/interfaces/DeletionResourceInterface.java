package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.PaperDTOs.DeletionPaperDTO;
import fr.pantheonsorbonne.global.UserIdentificationDTO;
import jakarta.ws.rs.core.Response;

public interface DeletionResourceInterface {
    Response deletePaper(DeletionPaperDTO deletionPaperDTO);
}
