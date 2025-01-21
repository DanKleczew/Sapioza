package fr.pantheonsorbonne.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private Long notifiedUserId;

    @Basic(optional = false)
    private Long paperId;

    @Basic(optional = false)
    private String authorFirstName;

    @Basic(optional = false)
    private String authorLastName;

    @Basic(optional = false)
    private String paperTitle;
    
    @Basic(optional = false)
    private Date notificationTime;

    public Notification(Long userId, Long paperId, String authorFirstName, String authorLastName, String paperTitle,
                        Date notificationTime) {
        this.notifiedUserId = userId;
        this.paperId = paperId;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.paperTitle = paperTitle;
        this.notificationTime = notificationTime;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotifiedUserId() {
        return notifiedUserId;
    }

    public void setNotifiedUserId(Long notifiedUserId) {
        this.notifiedUserId = notifiedUserId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }
    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }
    public String getAuthorLastName() {
        return authorLastName;
    }
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Date getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }
}
