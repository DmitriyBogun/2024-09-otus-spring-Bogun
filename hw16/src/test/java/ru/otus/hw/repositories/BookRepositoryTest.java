package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    private static final long AUTHOR_ID = 1L;
    private static final String AUTHOR_FULL_NAME = "Author_1";
    private static final long GENRE_ID = 1L;
    private static final String GENRE = "Genre_1";
    private static final long BOOK_ID = 1L;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldReturnCorrectBookById() {
        List<Book> expectedBooks = getBooks();
        for (Book expectedBook : expectedBooks) {
            Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
            assertThat(actualBook).isPresent()
                    .get()
                    .isEqualTo(expectedBook);
        }
    }

    @Test
    void shouldSaveNewBook() {

        Optional<Author> authorOptional = authorRepository.findByFullName(AUTHOR_FULL_NAME);
        Optional<Genre> optionalGenre = genreRepository.findByGenre(GENRE);

        Author author = authorOptional.get();
        Genre genre = optionalGenre.get();
        Book addedBook = new Book(null, "Test Book1", author, genre);

        entityManager.merge(addedBook);
        bookRepository.save(addedBook);
        entityManager.detach(addedBook);
        assertThat(addedBook.getId()).isGreaterThan(0);

        Book findBook = entityManager.find(Book.class, addedBook.getId());
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(addedBook);
    }


    @Test
    void shouldSaveUpdatedBook() {
        Author author = entityManager.find(Author.class, AUTHOR_ID);
        Genre genre = entityManager.find(Genre.class, GENRE_ID);

        Book expectedBook = new Book(BOOK_ID, "Test Book", author, genre);
        Book currentBook = entityManager.find(Book.class, expectedBook.getId());

        assertThat(currentBook)
                .usingRecursiveComparison()
                .isNotEqualTo(expectedBook);

        Book returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        Book findBook = entityManager.find(Book.class, returnedBook.getId());
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    private List<Book> getBooks() {
        return IntStream.range(1, 4).boxed()
                .map(id -> entityManager.find(Book.class, id))
                .toList();
    }
}