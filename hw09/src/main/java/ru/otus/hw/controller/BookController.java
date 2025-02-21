package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookServiceImpl bookService;

    private final AuthorServiceImpl authorService;

    private final GenreServiceImpl genreService;

    @GetMapping()
    public String allBooksList(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/all_books";
    }

    @GetMapping("/edit_book")
    public String editBook(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == null) {
            BookCreateDto book = new BookCreateDto(null, null, null, null);
            model.addAttribute("book", book);
        } else {
            BookDto book = bookService.findById(id);
            model.addAttribute("book", BookUpdateDto.fromBookDto(book));
        }

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "books/edit_book";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book",new BookCreateDto());
        return "books/new_book";
    }

    @PostMapping("/update_book")
    public String updateBook(@Valid @ModelAttribute("book") BookUpdateDto book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit_book?id=%d";
        }

        bookService.update(book);
        return "redirect:/book";
    }


    @PostMapping("/create_book")
    public String createBook(@Valid @ModelAttribute("book") BookCreateDto book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/book/new".formatted(book.getId());
        }

        bookService.create(book);
        return "redirect:/book";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
