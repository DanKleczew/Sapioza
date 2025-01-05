package fr.pantheonsorbonne.entity;

import fr.pantheonsorbonne.enums.ResearchField;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Paper {
    @Id
    private Long id;

    @Basic
    private String title;

    @Basic
    private long authorId;

    @Basic
    private ResearchField field;

    @Basic
    private String publishedIn;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Basic
    private String keywords;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
