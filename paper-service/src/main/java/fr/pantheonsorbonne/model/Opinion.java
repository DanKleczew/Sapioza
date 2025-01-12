package fr.pantheonsorbonne.model;

import jakarta.persistence.*;

@Entity
public class Opinion {

    @Id
    private Long id;

    @Basic(optional = false)
    private Boolean opinion;

    @Basic(optional = false)
    private Long userId;

    @ManyToOne(optional = false)
    private Paper paper;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOpinion(Boolean opinion) {
        this.opinion = opinion;
    }

    public Boolean getOpinion() {
        return opinion;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
    public Paper getPaper() {
        return paper;
    }
}
