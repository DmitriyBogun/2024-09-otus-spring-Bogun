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
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
@Import(AuthorMapper.class)
@WebFluxTest(controllers = AuthorController.class)
@ActiveProfiles("test")
class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    AuthorMapper authorMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void shouldGetAllAuthors() {
        Author[] authors = getExampleAuthors();
        given(authorRepository.findAll()).willReturn(Flux.just(authors));

        Flux<AuthorDto> fluxResult = webTestClient.get().uri("/api/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody();
        StepVerifier.FirstStep<AuthorDto> step = StepVerifier.create(fluxResult);
        StepVerifier.Step<AuthorDto> stepResult = null;
        for (Author author : authors) {
            stepResult = step.expectNext(authorMapper.toDto(author));
        }
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    private Author[] getExampleAuthors() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new Author(String.valueOf(id), "name %d".formatted(id)))
                .toArray(Author[]::new);
    }
}