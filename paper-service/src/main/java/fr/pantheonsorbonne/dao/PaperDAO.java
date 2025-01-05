package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dto.Filter;
import fr.pantheonsorbonne.entity.Paper;
import fr.pantheonsorbonne.enums.ResearchField;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PaperDAO {

    @Inject
    EntityManager em;

    @Transactional
    public Paper getPaper(long id){
        Paper paper = null;
        try {
            paper = em.find(Paper.class, id);
            Paper paper2 = new Paper();
            paper2.setTitle("test");
            paper2.setAuthorId(1);
            paper2.setField(ResearchField.ART);

            em.persist(paper2);
            em.flush();

        } catch (RuntimeException re) {
            Log.error("getPaperInfos failed", re);
        }
        return paper;
    }

    public List<Paper> filter(Filter filter) {
        List<Paper> papers = null;
        String query = "SELECT p FROM Paper p WHERE ";
        if (filter.title() != null) {
            query += "p.title = '" + filter.title() + "' AND ";
        }
        if (filter.authorId() != null) {
            query += "p.author = '" + filter.authorId() + "' AND ";
        }
        if (filter.abstract_() != null) {
            query += "p.abstract = '" + filter.abstract_() + "' AND ";
        }
        if (filter.keywords() != null) {
            query += "p.keywords = '" + filter.keywords() + "' AND ";
        }
        if (filter.revue() != null) {
            query += "p.revue = '" + filter.revue() + "' AND ";
        }
        if (filter.field() != null) {
            query += "p.field = '" + filter.field() + "' AND ";
        }

        if (query.endsWith("AND ")) {
            query = query.substring(0, query.length() - 5);
        } else {
            query = query.substring(0, query.length() - 7);
        }

        try {
            papers = em.createQuery(query, Paper.class).getResultList();
        } catch (RuntimeException re) {
            Log.error("getFilteredPapers failed", re);
        }
        return papers;
    }
}
