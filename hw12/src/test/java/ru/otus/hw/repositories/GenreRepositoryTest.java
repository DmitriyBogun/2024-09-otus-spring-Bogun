package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository jpaGenreRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final long GENRE_ID = 1L;

    @Test
    void findById() {
        Optional<Genre> optionalActualGenre = jpaGenreRepository.findById(GENRE_ID);
        Genre expectedGenre = entityManager.find(Genre.class, GENRE_ID);

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
