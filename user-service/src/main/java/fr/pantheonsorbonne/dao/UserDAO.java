package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.entity.User;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserDAO{
    @Inject
    EntityManager em;

    public User getUser(long id){
        User user = null;
        try {
            user = em.find(User.class, id);
        } catch (RuntimeException re) {
            Log.error("getUserInfos failed", re);
        }
        return user;

    }

    @Transactional
    public void addUser(User user){
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.flush();
            em.getTransaction().commit();
        } catch (RuntimeException re) {
            Log.error("addUser failed", re);
        }
    }
}
