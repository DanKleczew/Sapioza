package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dao.interfaces.QueryDAOInterface;
import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.Paper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class PaperQueryDAO implements QueryDAOInterface {

    @PersistenceContext
    private EntityManager em;

    public Paper getPaper(Long id) throws PaperDatabaseAccessException {
        Paper paper = null;
        try {
            paper = em.find(Paper.class, id);

        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return paper;
    }

    public List<Paper> getFilteredPapers(FilterDTO filter) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Paper> query = cb.createQuery(Paper.class);
            Root<Paper> root = query.from(Paper.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.title() != null) {
                predicates.add(cb.like(root.get("title"), "%" + filter.title().toLowerCase() + "%"));
            }
            if (filter.authorId() != null) {
                predicates.add(cb.equal(root.get("authorId"), filter.authorId()));
            }
            if (filter.keywords() != null) {
                predicates.add(cb.like(root.get("keywords"), "%" + filter.keywords().toLowerCase() + "%"));
            }
            if (filter.AscDate() != null) {
                query.orderBy(cb.asc(root.get("date")));
            }
            if (filter.DescDate() != null) {
                query.orderBy(cb.desc(root.get("date")));
            }

            //this.em.createQuery()
        } catch (RuntimeException re) {
            throw new PaperDatabaseAccessException();
        }
        return null;
    }

}
