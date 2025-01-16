package fr.pantheonsorbonne.model;

import fr.pantheonsorbonne.enums.ResearchField;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Entity
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Basic(optional = false)
    private String title;

    @Basic(optional = false)
    private Long authorId;

    @Basic
    private ResearchField field;

    @Basic
    private String publishedIn;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Basic
    private String keywords;

    @Basic(optional = false)
    private String abstract_;

    @Basic
    private String DOI;

    @OneToMany
    private List<Review> reviews;

    @OneToMany
    private List<Opinion> opinions;

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
    public void setAuthorId(Long authorId) {
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
    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }
    public String getAbstract_() {
        return abstract_;
    }
    public void setDOI(String DOI) { this.DOI = DOI; }
    public String getDOI() { return DOI; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public List<Review> getReviews() { return reviews; }
    public void setOpinions(List<Opinion> opinions) { this.opinions = opinions; }
    public List<Opinion> getOpinions() { return opinions; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getUuid() { return uuid; }
}
