package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(String title, String authorId, String genreId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Автор с таким id %d не найден, поищи другого=))".formatted(authorId))));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException((
                        "Жанр с таким id %d не найден, выбери что-нибудь поинтересней =))".formatted(genreId))));
        Book createdBook = new Book(null, title, author, genre);
        return bookRepository.save(createdBook);
    }

    @Override
    public Book update(String id, String title, String authorId, String genreId) {

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
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public List<Author> findAllAuthors() {
        List<Author> authors = new ArrayList<>();
        for (Book book : bookRepository.findDistinctAuthors()) {
            authors.add(book.getAuthor());
        }
        return authors;
    }
}
