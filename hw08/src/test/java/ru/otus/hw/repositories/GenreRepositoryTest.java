package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class GenreRepositoryTest {

    private static final String GENRE_ID = "1";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new Genre(GENRE_ID, "Test Genre"));
    }


    @Test
    void findById() {
        Optional<Genre> optionalActualGenre = genreRepository.findById(GENRE_ID);
        Genre expectedGenre = mongoTemplate.findById(GENRE_ID, Genre.class);

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }
}
