package ru.otus.hw.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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


@WebMvcTest(controllers = {BookController.class})
@Import(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest {

    private static final long BOOK_ID = 1L;

    private static final long ID_OF_THE_MISSING_BOOK = 1L;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookServiceImpl bookService;

    @MockitoBean
    private AuthorServiceImpl authorService;

    @MockitoBean
    private GenreServiceImpl genreService;
    @Autowired
    private BookController bookController;

    @Test
    void shouldAddNewBook() throws Exception {
        BookDto book = getSomeBook();
        BookCreateDto bookCreateDto = new BookCreateDto(null, book.getTitle(), book.getAuthor().getFullName(),
                book.getGenre().getGenre());

        mvc.perform(post("/book/create_book").flashAttr("book", bookCreateDto))
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = new BookDto(BOOK_ID, "B",
                new AuthorDto(1L, "A"), new GenreDto(1L, "G"));

        given(bookService.findById(BOOK_ID))
                .willReturn(bookDto);

        mvc.perform(get("/book/edit_book").param("id", String.valueOf(BOOK_ID)))
                .andExpect(status().isOk());
    }

    @Test()
    public void whenPostRequestToCreateBookAndInvalidBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto(null,"","test","test");
        //String book = "{\"id\": null,\"title\": \"\", \"authorName\" : \"author\", \"genre\": \"genre\"}";
        mvc.perform(MockMvcRequestBuilders.post("/book/create_book")
                .flashAttr("book", bookCreateDto))
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors());
    }

    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(ID_OF_THE_MISSING_BOOK))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/book/edit_book").param("id", String.valueOf(ID_OF_THE_MISSING_BOOK)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BookDto book = getSomeBook();
        given(bookService.findById(book.getId())).willReturn(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenre().getId());

        mvc.perform(post("/book/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/book/delete").param("id", String.valueOf(BOOK_ID)))
                .andExpect(redirectedUrl("/book"));
    }

    private BookDto getSomeBook() {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "Some Author")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "Some Genre")));

        return new BookDto(BOOK_ID, "Some Book",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
    }
}