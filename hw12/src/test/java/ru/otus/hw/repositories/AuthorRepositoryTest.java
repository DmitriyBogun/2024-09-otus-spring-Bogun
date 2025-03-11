package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository jpaAuthorRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final long AUTHOR_ID = 1L;

    @Test
    void findById() {
        Optional<Author> optionalActualGenre = jpaAuthorRepository.findById(AUTHOR_ID);
        Author expectedAuthor = entityManager.find(Author.class, AUTHOR_ID);

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
