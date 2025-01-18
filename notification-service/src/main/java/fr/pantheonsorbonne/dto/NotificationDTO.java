package fr.pantheonsorbonne.dto;

public class NotificationDTO {
    private Long id; // Unique identifier for the notification
    private Long userId; // ID of the user receiving the notification
    private Long paperId; // ID of the paper associated with the notification
    private String authorName; // Name of the author who published the paper
    private String paperTitle; // Title of the paper
    private boolean viewed; // Whether the notification has been viewed

    // Default constructor
    public NotificationDTO() {
    }

    // Parameterized constructor
    public NotificationDTO(Long id, Long userId, Long paperId, String authorName, String paperTitle, boolean viewed) {
        this.id = id;
        this.userId = userId;
        this.paperId = paperId;
        this.authorName = authorName;
        this.paperTitle = paperTitle;
        this.viewed = viewed;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Overriding toString() for better debugging and logging
    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", paperId=" + paperId +
                ", authorName='" + authorName + '\'' +
                ", paperTitle='" + paperTitle + '\'' +
                ", viewed=" + viewed +
                '}';
    }
}
