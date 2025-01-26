package fr.pantheonsorbonne.dao;

import fr.pantheonsorbonne.dto.FilterDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.model.Paper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
public class PaperQueryDAO   {

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
            if (filter.abstract_() != null) {
                predicates.add(cb.like(root.get("abstract_"), "%" + filter.abstract_().toLowerCase() + "%"));
            }
            if (filter.AscDate() != null && filter.AscDate()) {
                query.orderBy(cb.asc(root.get("publicationDate")));
            }
            if (filter.DescDate() != null && filter.DescDate()) {
                query.orderBy(cb.desc(root.get("publicationDate")));
            }
            if (filter.DOI() != null) {
                predicates.add(cb.equal(root.get("DOI"), filter.DOI()));
            }
            if (filter.field() != null) {
                predicates.add(cb.equal(root.get("field"), filter.field()));
            }
            if (filter.revue() != null) {
                predicates.add(cb.equal(root.get("publishedIn"), filter.revue()));
            }
            query.select(root).where(predicates.toArray(new Predicate[]{}));
            return em.createQuery(query).setMaxResults(filter.limit() != 0 ? filter.limit() : 100).getResultList();

        } catch (RuntimeException re) {
            Log.error("Error while filtering papers", re);
            throw new PaperDatabaseAccessException();
        }
    }

    public Paper getPaperByUuid(String uuid) throws PaperNotFoundException {
        try {
            return em.createQuery("SELECT p FROM Paper p WHERE p.uuid = :uuid", Paper.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (NoResultException e){
            throw new PaperNotFoundException(uuid);
        }
    }

}
