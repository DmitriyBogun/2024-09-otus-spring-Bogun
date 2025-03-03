package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    @ShellMethod(value = "findAll books", key = "FAB")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "findBook by id", key = "FBBID")
    public String findBookById(long id) {
        return bookService.findById(id)
                .map(Book::toString)
                .orElse("Book with id %d not found".formatted(id));
    }

    @ShellMethod(value = "insertBook", key = "INSB")
    public String insertBook(String title, long authorId, long genreId) {
        Book savedBook = bookService.create(title, authorId, genreId);
        return savedBook.toString();
    }

    @ShellMethod(value = "updateBook", key = "UPDB")
    public String updateBook(long id, String title, long authorId, long genreId) {
        Book savedBook = bookService.update(id, title, authorId, genreId);
        return savedBook.toString();
    }

    @ShellMethod(value = "deleteBook by id", key = "DELB")
    public void deleteBook(long id) {
        bookService.deleteById(id);
    }

}
