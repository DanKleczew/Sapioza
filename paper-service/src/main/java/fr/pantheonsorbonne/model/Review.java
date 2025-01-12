package fr.pantheonsorbonne.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Paper paper;

    @Basic(optional = false)
    private Long commentAuthorId;

    @Basic
    private String comment;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setPaper(Paper paper) {
        this.paper = paper;
    }
    public Paper getPaper() {
        return paper;
    }
    public void setCommentAuthorId(Long commentAuthorId) {
        this.commentAuthorId = commentAuthorId;
    }
    public Long getCommentAuthorId() {
        return commentAuthorId;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }
}
