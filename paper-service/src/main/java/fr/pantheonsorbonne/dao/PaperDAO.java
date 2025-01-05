package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.entity.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class PaperDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PaperUnity");


    public Paper getPaper(long id){
        EntityManager em = emf.createEntityManager();
        Paper paper = null;
        try {
            paper = em.find(Paper.class, id);
        } catch (RuntimeException re) {
            Log.error("getPaperInfos failed", re);
        }
        finally {
            em.close();
        }
        return paper;
    }
}
