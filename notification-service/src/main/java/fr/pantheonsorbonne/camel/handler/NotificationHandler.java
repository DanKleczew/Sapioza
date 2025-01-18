package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.service.NotificationCreationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificationHandler implements HandlerInterface {

    @Inject
    NotificationCreationService notificationCreationService;

    @Override
    public void handle(Long authorId) {
        try {
            Long paperId = 1L; // ID de l'article L c'est pour Long
            String authorName = "Author Name";
            notificationCreationService.notifyFollowers(authorId, paperId, authorName);
            Log.info("Notifications successfully processed for author ID: " + authorId);

        } catch (Exception e) {
            Log.error("Error while processing notifications for author ID: " + authorId, e);
        }
    }
}
