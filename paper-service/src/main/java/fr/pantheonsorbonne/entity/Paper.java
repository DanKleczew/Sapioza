package fr.pantheonsorbonne.entity;

import fr.pantheonsorbonne.enums.ResearchField;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
    public long getAuthorId() {
        return authorId;
    }
    public void setField(ResearchField field) {
        this.field = field;
    }
    public ResearchField getField() {
        return field;
    }
    public void setPublishedIn(String publishedIn) {
        this.publishedIn = publishedIn;
    }
    public String getPublishedIn() {
        return publishedIn;
    }
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    public Date getPublicationDate() {
        return publicationDate;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getKeywords() {
        return keywords;
    }

}
