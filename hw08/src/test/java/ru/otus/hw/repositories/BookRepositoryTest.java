package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BookRepositoryTest {

    private static final String AUTHOR_ID = "1";
    private static final String GENRE_ID = "1";
    private static final String BOOK_ID = "1";
    private static final String SECOND_AUTHOR_ID = "2";
    private static final String SECOND_GENRE_ID = "2";
    private static final String SECOND_BOOK_ID = "2";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        final Author king = new Author(AUTHOR_ID, "Стивен Кинг");
        final Genre horrors = new Genre(GENRE_ID, "Ужасы");
        mongoTemplate.save(new Book(BOOK_ID, "Свинка Пеппа", king, horrors));

        final Author govard = new Author(SECOND_AUTHOR_ID, "Говард Лавкрафт");
        final Genre nightmares = new Genre(SECOND_GENRE_ID, "Кошмары");
        mongoTemplate.save(new Book(SECOND_BOOK_ID, "Барашек Шон", govard, nightmares));
    }

    @Test
    void shouldReturnCorrectBookById() {
        List<Book> expectedBooks = getDbBooks();
        for(Book expectedBook : expectedBooks) {
            Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());

            assertThat(actualBook).isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBook);
        }
    }

    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> actualBooks = bookRepository.findAll();
        List<Book> expectedBooks = getDbBooks();

        assertThat(actualBooks)
                .usingRecursiveComparison()
                .isEqualTo(expectedBooks);
    }

    @Test
    void shouldSaveNewBook() {
        Author author = mongoTemplate.findById(AUTHOR_ID, Author.class);
        Genre genre = mongoTemplate.findById(GENRE_ID, Genre.class);
        Book addedBook = new Book(null, "TestBook", author, genre);

        bookRepository.save(addedBook);

        assertThat(addedBook.getId()).isNotEmpty();

        Book findBook = mongoTemplate.findById(addedBook.getId(), Book.class);
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(addedBook);
    }

    @Test
    void shouldSaveUpdatedBook() {
        Author author = mongoTemplate.findById(AUTHOR_ID, Author.class);
        Genre genre = mongoTemplate.findById(GENRE_ID, Genre.class);
        Book expectedBook = new Book(BOOK_ID, "TestBook", author, genre);
        Book currentBook = mongoTemplate.findById(expectedBook.getId(), Book.class);

        assertThat(currentBook)
                .usingRecursiveComparison()
                .isNotEqualTo(expectedBook);

        Book returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> !book.getId().isEmpty())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        Book findBook = mongoTemplate.findById(returnedBook.getId(), Book.class);
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @Test
    void shouldDeleteBook() {
        Book firstBook = mongoTemplate.findById(BOOK_ID, Book.class);
        assertThat(firstBook).isNotNull();
        bookRepository.deleteById(BOOK_ID);
        Book notFindedBook = mongoTemplate.findById(BOOK_ID, Book.class);
        assertThat(notFindedBook).isNull();
    }

    private List<Book> getDbBooks() {
        return mongoTemplate.findAll(Book.class);
    }

}
