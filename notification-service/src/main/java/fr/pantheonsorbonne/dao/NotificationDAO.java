package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.entity.NotificationEntity;
import fr.pantheonsorbonne.exception.NotificationDatabaseAccessException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<NotificationEntity> findNotificationsByAuthors(List<String> authors) {
        try {
            return entityManager.createQuery("SELECT n FROM NotificationEntity n WHERE n.authorName IN :authors", NotificationEntity.class)
                    .setParameter("authors", authors)
                    .getResultList();
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error fetching notifications for authors.", e);
        }
    }

    public void create(NotificationEntity notification) {
        try {
            entityManager.persist(notification);
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error persisting notification.", e);
        }
    }

    public List<NotificationEntity> findByUserId(Long userId) {
        try {
            return entityManager.createQuery("SELECT n FROM NotificationEntity n WHERE n.userId = :userId", NotificationEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error fetching notifications for user ID: " + userId, e);
        }
    }
}
