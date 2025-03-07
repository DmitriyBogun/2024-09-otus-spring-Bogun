package ru.otus.hw.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
@Import(GenreMapper.class)
@WebFluxTest(controllers = GenreController.class)
@ActiveProfiles("test")
class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GenreMapper genreMapper;

    @MockBean
    private GenreRepository genreRepository;


    @Test
    void shouldGetAllGenres() {
        Genre[] genres = getExampleGenres();
        given(genreRepository.findAll()).willReturn(Flux.just(genres));

        Flux<GenreDto> fluxResult = webTestClient.get().uri("/api/genre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(GenreDto.class)
                .getResponseBody();
        StepVerifier.FirstStep<GenreDto> step = StepVerifier.create(fluxResult);
        StepVerifier.Step<GenreDto> stepResult = null;
        for (Genre genre : genres) {
            stepResult = step.expectNext(genreMapper.toDto(genre));
        }

        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    private Genre[] getExampleGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(String.valueOf(id), "name %d".formatted(id)))
                .toArray(Genre[]::new);
    }
}