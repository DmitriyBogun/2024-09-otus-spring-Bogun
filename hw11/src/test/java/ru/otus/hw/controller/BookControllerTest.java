package ru.otus.hw.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
@Import({ BookMapper.class, GenreMapper.class, AuthorMapper.class })
@WebFluxTest(controllers = BookController.class)
@ActiveProfiles("test")
class BookControllerTest {

    private static final String BOOK_ID = "1";

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookMapper mapper;

    @Test
    void shouldGetAllBooks() {
        Book[] books = getExampleBooks();
        given(bookRepository.findAll()).willReturn(Flux.just(books));
        Flux<BookDto> fluxResult = webTestClient.get().uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();
        StepVerifier.FirstStep<BookDto> step = StepVerifier.create(fluxResult);
        StepVerifier.Step<BookDto> stepResult = null;
        for (Book book : books) {
            stepResult = step.expectNext(mapper.toDto(book));
        }
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @Test
    void shouldGetCorrectBook() {
        Book book = getTestBook();
        given(bookRepository.findById((String) any())).willReturn(Mono.just(book));

        Flux<BookDto> fluxResult = webTestClient.get().uri("/api/book/%s".formatted(book.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.FirstStep<BookDto> step = StepVerifier.create(fluxResult);
        StepVerifier.Step<BookDto> stepResult = step.expectNext(mapper.toDto(book));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @Test
    void shouldGetNotFoundEntity() {
        given(bookRepository.findById((String) any()))
                .willThrow(EntityNotFoundException.class);

        webTestClient.get()
                .uri("/api/book/".concat(BOOK_ID))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldAddNewBook() {
        Book book = getTestBook();

        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.save(any()))
                .willReturn(Mono.just(book));

        BookCreateDto bookCreateDto = new BookCreateDto(book.getId(),
                book.getTitle(), book.getAuthor().getId(),
                book.getGenre().getId());

        Flux<BookDto> fluxResult = webTestClient
                .post().uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreateDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.FirstStep<BookDto> step = StepVerifier.create(fluxResult);
        StepVerifier.Step<BookDto> stepResult = step.expectNext(mapper.toDto(book));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @Test
    void shouldUpdateBook() {
        Book book = getTestBook();

        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(book));
        given(bookRepository.save(any()))
                .willReturn(Mono.just(book));

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId());

        Flux<BookDto> fluxResult = webTestClient
                .patch().uri("/api/book/".concat(book.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookUpdateDto)
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.FirstStep<BookDto> step = StepVerifier.create(fluxResult);
        StepVerifier.Step<BookDto> stepResult = step.expectNext(mapper.toDto(book));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @Test
    void notFoundExceptionByUpdate() {
        Book book = getTestBook();

        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(book));
        given(bookRepository.save(any())).willThrow(EntityNotFoundException.class);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenre().getId());

        webTestClient.patch()
                .uri("/api/book/".concat(BOOK_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookUpdateDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldDeleteBook() {
        given(bookRepository.deleteById((String) any())).willReturn(Mono.empty());

        Flux<Void> fluxResult = webTestClient
                .delete().uri("/api/book/".concat(BOOK_ID))
                .exchange()
                .expectStatus().isNoContent()
                .returnResult(Void.class)
                .getResponseBody();

        StepVerifier.FirstStep<Void> step = StepVerifier.create(fluxResult);
        step.verifyComplete();
    }

    private Book[] getExampleBooks() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(String.valueOf(id), "title %d".formatted(id),
                        new Author(String.valueOf(id), "name"),
                        new Genre(String.valueOf(id), "genre")))
                .toArray(Book[]::new);
    }
    private Book getTestBook() {
        given(authorRepository.findAll()).willReturn(Flux.just(new Author("1", "Author")));
        given(genreRepository.findAll()).willReturn(Flux.just(new Genre("1", "Genre")));

        Author author = authorRepository.findAll().blockFirst();
        assertThat(author).isNotNull();

        Genre genre = genreRepository.findAll().blockFirst();
        assertThat(genre).isNotNull();

        return new Book(BOOK_ID, "Test book", author, genre);
    }
}