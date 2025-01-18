package fr.pantheonsorbonne.dto;

public class StoredPaperOutputDTO {
    private String paperUuid;
    private byte[] content; // Le contenu du String récupéré

    // Constructeurs
    public StoredPaperOutputDTO(String id, byte[] content) {
        this.paperUuid = id;
        this.content = content;
    }

    // Getters uniquement (lecture seule pour l'appelant)
    public String getId() {
        return paperUuid;
    }

    public byte[] getContent() {
        return content;
    }
}

