package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcBookRepository.class, JdbcGenreRepository.class})
class JdbcBookRepositoryTest {

    @Autowired
    private JdbcBookRepository repositoryJdbc;

    private List<Author> authors;

    private List<Genre> genres;

    private List<Book> books;

    @BeforeEach
    void setUp() {
        authors = getAuthors();
        genres = getGenres();
        books = getBooks(authors, genres);
    }

    @ParameterizedTest
    @MethodSource("getBooks")
    void shouldReturnCorrectBookById(Book expectedBook) {
        Optional<Book> actualBook = repositoryJdbc.findById(expectedBook.getId());
        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> actualBooks = repositoryJdbc.findAll();
        List<Book> expectedBooks = books;

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    @Test
    void shouldSaveNewBook() {
        Book expectedBook = new Book(0, "TestBook1", authors.get(0), genres.get(0));
        Book returnedBook = repositoryJdbc.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repositoryJdbc.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @Test
    void shouldSaveUpdatedBook() {
        Book expectedBook = new Book(1L, "TestBook3", authors.get(2), genres.get(2));

        assertThat(repositoryJdbc.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        Book returnedBook = repositoryJdbc.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repositoryJdbc.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @Test
    void shouldDeleteBookById() {
        assertThat(repositoryJdbc.findById(1L)).isPresent();
        repositoryJdbc.deleteById(1L);
        assertThat(repositoryJdbc.findById(1L)).isEmpty();
    }

    private static List<Author> getAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author" + id))
                .toList();
    }

    private static List<Genre> getGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre" + id))
                .toList();
    }

    private static List<Book> getBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "TestBook" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<Book> getBooks() {
        List<Author> authors = getAuthors();
        List<Genre> genres = getGenres();
        return getBooks(authors, genres);
    }
}