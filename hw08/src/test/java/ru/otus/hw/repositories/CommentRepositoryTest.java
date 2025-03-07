package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COMMENT_ID = "1";

    private static final String BOOK_ID = "1";

    private static final String BOOK_SECOND_ID = "2";

    private static final String NEW_COMMENT = "Test comment";

    @BeforeEach
    public void initialize() {
        Book firstBook = new Book(BOOK_ID, "TestBook1", null, null);
        mongoTemplate.save(firstBook);
        Book secondBook = new Book(BOOK_SECOND_ID, "TestBook2", null, null);
        mongoTemplate.save(secondBook);

        mongoTemplate.save(new Comment(COMMENT_ID, "TestText", firstBook));
    }

    @Test
    void shouldAddNewComment() {
        Book firstBook = mongoTemplate.findById(BOOK_ID, Book.class);

        Comment addedComment = new Comment(null, NEW_COMMENT, firstBook);
        commentRepository.save(addedComment);
        Comment findComment = mongoTemplate.findById(addedComment.getId(), Comment.class);

        assertThat(addedComment)
                .usingRecursiveComparison()
                .isEqualTo(findComment);
    }

    @Test
    void shouldChangeComment() {
        Book secondBook = mongoTemplate.findById(BOOK_SECOND_ID, Book.class);
        Comment previousComment = mongoTemplate.findById(COMMENT_ID, Comment.class);

        Comment updatedComment = commentRepository.save(new Comment(COMMENT_ID, NEW_COMMENT, secondBook));
        Comment findComment = mongoTemplate.findById(COMMENT_ID, Comment.class);

        assertThat(updatedComment)
                .usingRecursiveComparison()
                .isNotEqualTo(previousComment)
                .isEqualTo(findComment);
    }

    @Test
    void shouldFindExpectedCommentById() {
        Optional<Comment> optionalActualComment = commentRepository.findById(COMMENT_ID);
        Comment expectedComment = mongoTemplate.findById(COMMENT_ID, Comment.class);

        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "text")
                .isEqualTo(expectedComment);
    }
}
