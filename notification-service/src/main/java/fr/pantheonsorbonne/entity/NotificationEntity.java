package fr.pantheonsorbonne.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean viewed;
    private Long userId;
    private Long paperId;
    private LocalDateTime notificationTime;
    private String authorName;
    private String paperTitle;


    public NotificationEntity() {
    }

    public NotificationEntity(boolean viewed, Long userId, Long paperId, LocalDateTime notificationTime,String authorName,String paperTitle) {
        this.viewed = viewed;
        this.userId = userId;
        this.paperId = paperId;
        this.notificationTime = notificationTime;
        this.authorName = authorName;
        this.paperTitle = paperTitle;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPaperTitle() {
        return paperTitle;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }
}
