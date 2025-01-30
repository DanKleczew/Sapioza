package fr.pantheonsorbonne.dto;

import fr.pantheonsorbonne.enums.ResearchField;

public record FilterDTO(String title,
                        Long authorId,
                        String abstract_,
                        String keywords,
                        String revue,
                        ResearchField field,
                        Boolean AscDate,
                        Boolean DescDate,
                        String DOI,
                        int limit) {
    // Filter for paper search


    @Override
    public String toString() {
        return "FilterDTO{" +
                "title='" + title + '\'' +
                ", authorId=" + authorId +
                ", abstract_='" + abstract_ + '\'' +
                ", keywords='" + keywords + '\'' +
                ", revue='" + revue + '\'' +
                ", field=" + field +
                ", AscDate=" + AscDate +
                ", DescDate=" + DescDate +
                ", DOI='" + DOI + '\'' +
                ", limit=" + limit +
                '}';
    }
}
