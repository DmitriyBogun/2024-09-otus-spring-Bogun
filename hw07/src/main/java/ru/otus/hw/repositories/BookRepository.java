package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {

    @Nonnull
    @EntityGraph("book_graph")
    Optional<Book> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("book_graph")
    List<Book> findAll();
}
