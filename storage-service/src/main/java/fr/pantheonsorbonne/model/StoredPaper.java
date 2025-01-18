package fr.pantheonsorbonne.model;

import jakarta.persistence.*;

@Entity
public class StoredPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paperUuid; // Identifiant transmis

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] body; // Stocke le contenu PDF en tant que tableau d'octets

    // Getter et Setter pour id
    public Long getId() {
        return id;
    }

    // Getter et Setter pour paperUuid
    public String getPaperUuid() {
        return paperUuid;
    }

    public void setPaperUuid(String paperUuid) {
        this.paperUuid = paperUuid;
    }

    // Getter et Setter pour body
    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}

