package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.model.Notification;
import fr.pantheonsorbonne.exception.NotificationDatabaseAccessException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    EntityManager entityManager;

    //Crée une nouvelle notification dans la base de données - notification Entité Notification à persister.

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

    //Recherche les notifications pour un utilisateur donné. ID de l'utilisateur. Liste des notifications de l'utilisateur.

    public List<Notification> findByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }
        try {
            return entityManager.createQuery(
                            "SELECT n FROM Notification n WHERE n.userId = :userId", Notification.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            throw new NotificationDatabaseAccessException("Error fetching notifications for user ID: " + userId, e);
        }
    }

    @Transactional
    public void persistAll(List<Notification> notifications) {
        entityManager.getTransaction().begin();
        for (Notification notification : notifications) {
            entityManager.persist(notification);
        }
        entityManager.getTransaction().commit();
    }
}
