package fr.pantheonsorbonne.resources.interfaces;

import fr.pantheonsorbonne.dto.PaperDTO;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface QueryResourceInterface {

    Response getPaperInfos(Long id);

    Response getFilteredPapers(String title,
                                     Long authorId,
                                     String abstract_,
                                     String keywords,
                                     String revue,
                                     String field);
}
