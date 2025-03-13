package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("book-graph")
    List<Book> findAll();

    @EntityGraph("book-graph")
    Optional<Book> findById(Long id);

    @RestResource(path = "book", rel = "book")
    List<Book> findByTitle(String title);

    @RestResource(path = "del_book", rel = "del_book")
    void deleteBookByTitle(String title);
}
