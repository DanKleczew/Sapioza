package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.model.Notification;
import fr.pantheonsorbonne.exception.NotificationDatabaseAccessException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Notification> findNotificationsByAuthors(List<String> authors) {
        try {
            return entityManager.createQuery("SELECT n FROM Notification n WHERE n.authorName IN :authors", Notification.class)
                    .setParameter("authors", authors)
                    .getResultList();
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error fetching notifications for authors.", e);
        }
    }

    @Transactional
    public void create(Notification notification) {
        try {
            entityManager.persist(notification);
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error persisting notification.", e);
        }
    }

    public List<Notification> findByUserId(Long userId) {
        try {
            return entityManager.createQuery("SELECT n FROM Notification n WHERE n.userId = :userId", Notification.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error fetching notifications for user ID: " + userId, e);
        }
    }

    @Transactional
    public void createTestNotification() {
        Notification notification = new Notification();
        try {
            notification.setAuthorName("test_author");
            notification.setPaperTitle("test_title");
            notification.setUserId(1L); // ID utilisateur de test
            notification.setNotificationTime(LocalDateTime.now());
            notification.setViewed(false);

            entityManager.persist(notification);
            Log.info("Test notification created successfully");
        } catch (RuntimeException re) {
            Log.error("createTestNotification failed", re);
        }
    }

}
