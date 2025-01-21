package fr.pantheonsorbonne.test;

public class EmailNotificationDTO {
    private String email;
    private String title;
    private String authorName;
    private String link;

    public EmailNotificationDTO(String email, String title, String authorName, String link) {
        this.email = email;
        this.title = title;
        this.authorName = authorName;
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
