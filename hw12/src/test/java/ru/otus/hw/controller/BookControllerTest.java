package ru.otus.hw.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.impl.AuthorServiceImpl;
import ru.otus.hw.services.impl.BookServiceImpl;
import ru.otus.hw.services.impl.GenreServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;
    @Autowired
    private BookController bookController;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldAddNewBook() throws Exception {
        BookDto book = getSomeBook();
        BookCreateDto bookCreateDto = new BookCreateDto(null, book.getTitle(), book.getAuthor().getId(),
                book.getGenre().getId());

        mvc.perform(post("/book/create_book")
                        .with(csrf())
                        .flashAttr("book", bookCreateDto))
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    void shouldReturn401ForCreateBook() throws Exception {
        mvc.perform(get("/book/create_book"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = new BookDto(BOOK_ID, "TestBook",
                new AuthorDto(1L, "TestAuthor"), new GenreDto(1L, "TestGenre"));

        given(bookService.findById(BOOK_ID))
                .willReturn(bookDto);

        mvc.perform(get("/book/edit_book").param("id", String.valueOf(BOOK_ID)))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test()
    public void whenPostRequestToCreateBookAndInvalidBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto(null,"Test",1L,1L);

        mvc.perform(MockMvcRequestBuilders.post("/book/create_book")
                        .with(csrf())
                .flashAttr("book", bookCreateDto))
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(ID_OF_THE_MISSING_BOOK))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/book/edit_book").param("id", String.valueOf(ID_OF_THE_MISSING_BOOK)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturn401ForUpdateBook() throws Exception {
        mvc.perform(get("/book/update_book"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldUpdateBook() throws Exception {
        BookDto book = getSomeBook();
        given(bookService.findById(book.getId())).willReturn(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenre().getId());

        mvc.perform(post("/book/update_book")
                        .with(csrf())
                        .flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    void shouldReturn401ForDeleteBook() throws Exception {
        mvc.perform(get("/book/delete"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/book/delete")
                        .with(csrf())
                        .param("id", String.valueOf(BOOK_ID)))
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