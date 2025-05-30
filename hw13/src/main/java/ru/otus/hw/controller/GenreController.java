package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.impl.GenreServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreServiceImpl genreService;

    @GetMapping("/genres")
    public String allGenresList(Model model) {
        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres/genres_list";
    }
}
