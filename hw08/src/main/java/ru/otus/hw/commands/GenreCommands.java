package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    @ShellMethod(value = "findAll genres", key = "FAG")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(Genre::toString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "delete genre by id", key = "DG")
    public void deleteGenreById(String id) {
        genreService.deleteById(id);
    }
}
