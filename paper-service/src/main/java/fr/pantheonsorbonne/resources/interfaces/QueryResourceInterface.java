package fr.pantheonsorbonne.resources.interfaces;

import jakarta.ws.rs.core.Response;

public interface QueryResourceInterface {

    Response getPaperInfos(Long id);

    Response getFilteredPapers(String title,
                               Long authorId,
                               String abstract_,
                               String keywords,
                               String revue,
                               String field,
                               boolean AscDate,
                               boolean DescDate,
                               String DOI);

    Response getPaperReviews(Long id);

    Response getPaperPdf(Long id);
}
