package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long BOOK_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @Test
    void shouldGetAllBooks() throws Exception {
        List<BookDto> bookList = getBookList();

        given(bookService.findAll()).willReturn(bookList);
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookList)));
    }

    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = getBookDto();

        given(bookService.findById(any())).willReturn(bookDto);

        mvc.perform(get("/api/book/%d".formatted(bookDto.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(any()))
                .willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/book/%d".formatted(BOOK_ID)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddNewBook() throws Exception {
        BookDto bookDto = getBookDto();
        given(bookService.create(any()))
                .willReturn(bookDto);

        BookCreateDto bookCreateDto = new BookCreateDto(bookDto.getId(),
                bookDto.getTitle(), bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BookDto bookDto = getBookDto();
        given(bookService.update(any()))
                .willReturn(bookDto);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());

        mvc.perform(patch("/api/book/%d".formatted(bookDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    void notFoundExceptionByUpdate() throws Exception {
        given(bookService.update(any())).willThrow(EntityNotFoundException.class);

        BookDto bookDto = getBookDto();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor().getId(), bookDto.getGenre().getId());
        mvc.perform(patch("/api/book/%d".formatted(BOOK_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/book/%d".formatted(BOOK_ID)))
                .andExpect(status().isNoContent());
    }

    private List<BookDto> getBookList() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new BookDto(id, "title %d".formatted(id),
                        new AuthorDto(id, "test name"), new GenreDto(id, "test genre")))
                .toList();
    }

    private BookDto getBookDto() {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "test author")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "test genre")));

        return new BookDto(BOOK_ID, "Test book",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
    }
}