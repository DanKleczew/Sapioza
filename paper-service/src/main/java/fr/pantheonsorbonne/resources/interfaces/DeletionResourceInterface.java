package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.UserIdentificationDTO;
import jakarta.ws.rs.core.Response;

public interface DeletionResourceInterface {
    Response deletePaper(Long id, UserIdentificationDTO userIdentificationDTO);
}
