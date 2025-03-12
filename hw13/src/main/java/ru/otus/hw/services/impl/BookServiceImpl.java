package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;


    @PreAuthorize("hasRole('USER')")
    @Transactional(readOnly = true)
    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id = %d is not found"
                        .formatted(id))));
    }


    @PreAuthorize("hasRole('USER')")
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream().map(bookMapper::toDto)
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public BookDto create(BookCreateDto bookDto) {
        final Long authorId = bookDto.getAuthorId();
        final Long genreId = bookDto.getGenreId();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        Book book = bookMapper.toModel(bookDto, author, genre);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookDto) {
        final Long id = bookDto.getId();
        final Long authorId = bookDto.getAuthorId();
        final Long genreId = bookDto.getGenreId();

        bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Book with id %d not found".formatted(id)));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author with id %d not found".formatted(authorId)));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Genre with id %d not found".formatted(genreId)));
        Book book = bookMapper.toModel(bookDto, author, genre);
        return bookMapper.toDto(bookRepository.save(book));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
