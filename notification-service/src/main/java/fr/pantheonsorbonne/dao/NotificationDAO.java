package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.model.Notification;
import fr.pantheonsorbonne.exception.NotificationDatabaseAccessException;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null.");
        }
        try {
            entityManager.persist(notification);
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error persisting notification.", e);
        }
    }

    @Transactional
    public List<Notification> findByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }
        try {
            return entityManager.createQuery(
                            "SELECT n FROM Notification n WHERE n.notifiedUserId = :userId ORDER BY n.notificationTime desc", Notification.class)
                    .setParameter("userId", userId)
                    .setMaxResults(10)
                    .getResultList();
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error fetching notifications for user ID: " + userId, e);
        }
    }

    @Transactional
    public void persistAll(List<Notification> notifications) {
        try {
            for (Notification notification : notifications) {
                entityManager.persist(notification);
            }
        } catch (RuntimeException e) {
            Log.error("Failed to persist notifications.", e);
        }

    }
}
