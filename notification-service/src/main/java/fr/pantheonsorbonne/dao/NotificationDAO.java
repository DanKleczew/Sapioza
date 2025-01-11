package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.entity.NotificationEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<NotificationEntity> findNotificationsByAuthors(List<String> authors) {
        return entityManager.createQuery("SELECT n FROM NotificationEntity n WHERE n.authorName IN :authors", NotificationEntity.class)
                .setParameter("authors", authors)
                .getResultList();
    }

    public void create(NotificationEntity notification) {
        entityManager.persist(notification);
    }

    public List<NotificationEntity> findByUserId(Long userId) {
        return entityManager.createQuery("SELECT n FROM NotificationEntity n WHERE n.userId = :userId", NotificationEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
