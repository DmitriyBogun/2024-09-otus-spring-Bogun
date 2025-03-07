package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping()
    public Flux<BookDto> allBooksList() {
        Flux<BookDto> bookDtoFlux = bookRepository.findAll().map(bookMapper::toDto);
        return bookDtoFlux;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable(value = "id", required = false) String id) {
        Mono<ResponseEntity<BookDto>> responseEntityMono = bookRepository.findById(id)
                .map(bookMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
        return responseEntityMono;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Mono<ResponseEntity<BookDto>> updateBook(@PathVariable ("id") String id,
            @Valid @RequestBody BookUpdateDto book) {
        book.setId(id);

        final String authorId = book.getAuthorId();
        final String genreId = book.getGenreId();

        Mono<ResponseEntity<BookDto>> responseEntityMono = bookRepository.findById(id)
                .flatMap(findedBook -> authorRepository.findById(authorId)
                        .flatMap(author -> genreRepository.findById(genreId)
                                .flatMap(genre -> bookRepository
                                        .save(bookMapper.toModel(book, author, genre))
                                )
                                .map(returnedBook -> new ResponseEntity<>(
                                        bookMapper.toDto(returnedBook), HttpStatus.ACCEPTED))
                                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()))));

        return responseEntityMono;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<ResponseEntity<BookDto>> createBook(@Valid @RequestBody BookCreateDto book) {

        String authorId = book.getAuthorId();
        String genreId = book.getGenreId();

        Mono<ResponseEntity<BookDto>> responseEntityMono = authorRepository.findById(authorId)
                .flatMap(author -> genreRepository.findById(genreId)
                        .flatMap(genre -> bookRepository
                                .save(bookMapper
                                        .toModel(book, author, genre)))
                        .map(savedBook -> new ResponseEntity<>(bookMapper.toDto(savedBook), HttpStatus.CREATED))
                        .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build())));
        return responseEntityMono;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") String id) {

        Mono<ResponseEntity<Void>> entityMono = bookRepository.deleteById(id)
                .then(Mono.fromCallable(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT)));

        return entityMono;
    }
}
