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

    public List<Book> autoComplete(BookFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        switch (filter.field) {
            case TITLE:
                criteria.where(cb.like(root.get("title"), "%" + filter.text + "%"));
                break;
            case AUTHOR:
                criteria.where(cb.like(root.get("title"), "%" + filter.text + "%"));
                break;
            default:
                break;
        }
        return em.createQuery(criteria).getResultList();
    }
}
