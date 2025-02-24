package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {
    private final GenreServiceImpl genreService;

    @GetMapping("/api/genres")
    public List<GenreDto> allGenresList() {
        return genreService.findAll();
    }
}
