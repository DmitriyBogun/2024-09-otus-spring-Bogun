package ru.otus.hw.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaAuthorRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "select distinct a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
