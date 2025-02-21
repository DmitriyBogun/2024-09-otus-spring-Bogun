package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Book insert(String title, long authorId, long genreId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Автор с таким id %d не найден, поищи другого=))".formatted(authorId))));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Жанр с таким id %d не найден, выбери что-нибудь поинтересней =))".formatted(genreId))));
        Book createdBook = new Book(0L, title, author, genre);
        return bookRepository.save(createdBook);
    }

    @Override
    @Transactional
    public Book update(long id, String title, long authorId, long genreId) {

        Book updatedBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Книга с таким id %d не найдена, давай обновим другую)".formatted(id))));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Автор с таким id %d не найден, поищи другого=))".formatted(authorId))));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Жанр с таким id %d не найден, выбери что-нибудь поинтересней =))".formatted(genreId))));

        updatedBook.setTitle(title);
        updatedBook.setAuthor(author);
        updatedBook.setGenre(genre);

        return bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
