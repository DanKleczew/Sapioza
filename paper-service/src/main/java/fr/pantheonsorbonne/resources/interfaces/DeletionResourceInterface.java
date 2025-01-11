package fr.pantheonsorbonne.resources.interfaces;

import jakarta.ws.rs.core.Response;

public interface DeletionResourceInterface {
    Response deletePaper(Long id);
}
