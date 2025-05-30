package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;


@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @GetMapping("/api/genre")
    public Flux<GenreDto> allGenresList() {
        Flux<GenreDto> genreDtoFlux = genreRepository.findAll().map(genreMapper::toDto);
        return genreDtoFlux;
    }
}
