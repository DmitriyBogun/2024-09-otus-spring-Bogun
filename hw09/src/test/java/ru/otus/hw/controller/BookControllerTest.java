package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тестирование контроллера книг")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long FIRST_BOOK_ID = 1L;

    private static final long NOT_CONTAIN_BOOK_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookServiceImpl bookService;

    @MockitoBean
    private AuthorServiceImpl authorService;

    @MockitoBean
    private GenreServiceImpl genreService;

    @Test
    void shouldAddNewBook() throws Exception {
        BookDto book = getExampleOfBookDto();
        BookCreateDto bookCreateDto = new BookCreateDto(null, book.getTitle(), book.getAuthor().getFullName(),
                book.getGenre().getGenre());

        mvc.perform(post("/book/create_book").flashAttr("book", bookCreateDto))
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = new BookDto(FIRST_BOOK_ID, "B",
                new AuthorDto(1L, "A"), new GenreDto(1L, "G"));

        given(bookService.findById(FIRST_BOOK_ID))
                .willReturn(bookDto);

        mvc.perform(get("/book/edit_book").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(NOT_CONTAIN_BOOK_ID))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/book/edit_book").param("id", String.valueOf(NOT_CONTAIN_BOOK_ID)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BookDto book = getExampleOfBookDto();
        given(bookService.findById(book.getId())).willReturn(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenre().getId());

        mvc.perform(post("/book/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/book"));
    }

    @DisplayName("Должен удалить книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/book/delete").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(redirectedUrl("/book"));
    }

    private BookDto getExampleOfBookDto() {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "G")));

        return new BookDto(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
    }
}