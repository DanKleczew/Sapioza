package fr.pantheonsorbonne.model;

import jakarta.persistence.*;

@Entity
public class StoredPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paperUuid;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Byte[] body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaperUuid() {
        return paperUuid;
    }

    public void setPaperUuid(String paperUuid) {
        this.paperUuid = paperUuid;
    }

    public Byte[] getBody() {
        return body;
    }

    public void setBody(Byte[] body) {
        this.body = body;
    }
}

