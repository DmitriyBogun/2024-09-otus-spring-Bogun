package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class AuthorRepositoryTest {
    private static final String AUTHOR_ID = "1";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new Author(AUTHOR_ID, "Test Author"));
    }

    @Test
    void findById() {
        Optional<Author> optionalActualAuthor = authorRepository.findById(AUTHOR_ID);
        Author expectedAuthor = mongoTemplate.findById(AUTHOR_ID, Author.class);

        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }
}
