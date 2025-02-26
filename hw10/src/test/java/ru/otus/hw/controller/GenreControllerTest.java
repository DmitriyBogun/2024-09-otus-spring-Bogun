package ru.otus.hw.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreServiceImpl genreService;


    @Test
    void shouldGetAllGenres() throws Exception {
        List<GenreDto> genreList = getGenreList();

        given(genreService.findAll()).willReturn(genreList);
        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genreList)));
    }

    private List<GenreDto> getGenreList() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new GenreDto(id, "name %d".formatted(id)))
                .toList();
    }
}