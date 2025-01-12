package fr.pantheonsorbonne.dto;

public class StoredPaperOutputDTO {
    private Long id;
    private String content; // Le contenu du String récupéré

    // Constructeurs
    public StoredPaperOutputDTO(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    // Getters uniquement (lecture seule pour l'appelant)
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

