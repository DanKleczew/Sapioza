package fr.pantheonsorbonne.dto;

public class StoredPaperInputDTO {
    private Long id;
    private String content; // Le contenu du String à stocker

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

