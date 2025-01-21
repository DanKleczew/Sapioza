package fr.pantheonsorbonne.model;

import fr.pantheonsorbonne.enums.OpinionList;
import jakarta.persistence.*;

@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private OpinionList opinion;

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

    public void setOpinion(OpinionList opinion) {
        this.opinion = opinion;
    }

    public OpinionList getOpinion() {
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
