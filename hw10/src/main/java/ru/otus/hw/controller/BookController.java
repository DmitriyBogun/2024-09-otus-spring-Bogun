package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping()
    public List<BookDto> allBooksList() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable(value = "id", required = false) Long id) {
        return bookService.findById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public BookDto updateBook(@PathVariable ("id") Long id,
            @Valid @RequestBody BookUpdateDto book) {
        book.setId(id);
        return bookService.update(book);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookCreateDto book) {
        return bookService.create(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
