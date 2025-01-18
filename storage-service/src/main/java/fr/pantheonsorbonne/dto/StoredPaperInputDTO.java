package fr.pantheonsorbonne.dto;

public class StoredPaperInputDTO {
    private String paperUuid;
    private byte[] content;

    public StoredPaperInputDTO(String paperUuid, byte[] content) {
        this.paperUuid = paperUuid;
        this.content = content;
    }

    // Getters et Setters
    public String getId() {
        return paperUuid;
    }

    public void setId(String id) {
        this.paperUuid = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

