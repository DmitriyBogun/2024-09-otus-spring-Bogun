package ru.otus.hw.repositories.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaBookRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where id = :id",
                Book.class);
        query.setParameter("id", id);
        query.setHint(FETCH.getKey(), entityGraph);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_graph");

        TypedQuery<Book> query = em.createQuery(
                "select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);

        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
