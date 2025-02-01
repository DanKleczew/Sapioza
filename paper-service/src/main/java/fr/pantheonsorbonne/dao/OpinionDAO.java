package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.exception.OpinionNotFoundException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Opinion;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class OpinionDAO {

    @PersistenceContext
    private EntityManager em;

    public void removeOpinion(Opinion opinion) throws OpinionNotFoundException {
        try {
            Opinion foundOp = this.findOpinion(opinion);
            em.remove(foundOp);
        } catch (NoResultException e) {
            throw new OpinionNotFoundException(opinion.getPaper().getId(), opinion.getUserId());
        } catch (NonUniqueResultException e) {
            this.removeAllOpinions(opinion);
        } catch (RuntimeException e) {
            throw new PaperDatabaseAccessException();
        }
    }

    public void changeOpinion(Opinion opinion) {
        try {
            Opinion foundOp = this.findOpinion(opinion);
            foundOp.setOpinion(opinion.getOpinion());
            em.merge(foundOp);
        } catch (OpinionNotFoundException e) {
            em.persist(opinion);
        } catch (NonUniqueResultException e) {
            this.removeAllOpinions(opinion);
            em.persist(opinion);
        } catch (RuntimeException e) {
            throw new PaperDatabaseAccessException();
        }
    }

    private void removeAllOpinions(Opinion opinion) {
        em.createQuery("delete from Opinion o where o.userId = :userId and o.paper = :paper")
                .setParameter("userId", opinion.getUserId())
                .setParameter("paper", opinion.getPaper());
    }

    public Opinion findOpinion(Opinion opinion) throws OpinionNotFoundException {
        try {
            return em.createQuery("select o from Opinion o where o.paper = :paper and o.userId = :userId", Opinion.class)
                    .setParameter("paper", opinion.getPaper())
                    .setParameter("userId", opinion.getUserId())
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new OpinionNotFoundException(opinion.getPaper().getId(), opinion.getUserId());
        }
        catch (RuntimeException e){
            throw new PaperDatabaseAccessException();
        }
    }

    public List<Opinion> getAllOpinions(Paper paper) {
        try {
            return em.createQuery("select o from Opinion o where o.paper = :paper", Opinion.class)
                    .setParameter("paper", paper)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new PaperDatabaseAccessException();
        }
    }

    public void removeOpinions(Long id) {
        em.createQuery("delete from Opinion o where o.paper.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
