package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.BookFilter;
import by.hackaton.bookcrossing.entity.Book;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class BookService {
    @PersistenceContext
    private EntityManager em;

    public List<String> autoComplete(BookFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        Root<Book> root = criteria.distinct(true).from(Book.class);
        switch (filter.field) {
            case TITLE:
                criteria.select(root.get("title")).where(cb.like(root.get("title"), "%" + filter.text.toLowerCase() + "%"));
                break;
            case AUTHOR:
                criteria.select(root.get("author")).where(cb.like(root.get("author"), "%" + filter.text.toLowerCase() + "%"));
                break;
            default:
                break;
        }
        return em.createQuery(criteria).getResultList();
    }
}
