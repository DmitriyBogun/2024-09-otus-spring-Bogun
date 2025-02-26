package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.*;

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
