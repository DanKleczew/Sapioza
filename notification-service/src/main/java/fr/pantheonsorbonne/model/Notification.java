package fr.pantheonsorbonne.model;

import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Basic(optional = false)
    private Long userId;

    @Basic(optional = false)
    private Long paperId;

    @Basic(optional = false)
    private String authorName;

    @Basic(optional = false)
    private String paperTitle;

    @Basic(optional = false)
    private boolean viewed;

    @Basic(optional = false)
    private Date notificationTime;

    public Notification() {
    }

    public Notification(Long userId, Long paperId, String authorName, String paperTitle,
                        boolean viewed, Date notificationTime) {
        this.userId = userId;
        this.paperId = paperId;
        this.authorName = authorName;
        this.paperTitle = paperTitle;
        this.viewed = viewed;
        this.notificationTime = notificationTime;
    }


    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Date getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userId=" + userId +
                ", paperId=" + paperId +
                ", authorName='" + authorName + '\'' +
                ", paperTitle='" + paperTitle + '\'' +
                ", notificationTime=" + notificationTime +
                '}';
    }


    // faire la méthode persistNotifications


}


/* ancienne version
package fr.pantheonsorbonne.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Basic(optional = false)
    private Long userId; // ID de l'utilisateur recevant la notification

    @Basic(optional = false)
    private Long paperId; // ID de l'article lié à la notification

    @Basic(optional = false)
    private String authorName; // Nom de l'auteur de l'article

    @Basic(optional = false)
    private String paperTitle; // Titre de l'article lié

    @Basic(optional = false)
    private boolean viewed; // Indique si la notification a été vue

    @Basic(optional = false)
    private LocalDateTime notificationTime; // Date et heure de la notification

    public Notification() {
    }

    public Notification(Long userId, Long paperId, String authorName, String paperTitle, boolean viewed, LocalDateTime notificationTime) {
        this.userId = userId;
        this.paperId = paperId;
        this.authorName = authorName;
        this.paperTitle = paperTitle;
        this.viewed = viewed;
        this.notificationTime = notificationTime;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userId=" + userId +
                ", paperId=" + paperId +
                ", authorName='" + authorName + '\'' +
                ", paperTitle='" + paperTitle + '\'' +
                ", viewed=" + viewed +
                ", notificationTime=" + notificationTime +
                '}';
    }
}

 */