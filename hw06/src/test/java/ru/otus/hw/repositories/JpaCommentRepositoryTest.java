package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.impl.JpaCommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCommentRepository.class)
@AutoConfigureTestEntityManager
public class JpaCommentRepositoryTest {
    @Autowired
    private JpaCommentRepository commentRepositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    private static final long COMMENT_ID = 1L;

    private static final long BOOK_FIRST_ID = 1L;

    private static final long BOOK_SECOND_ID = 2L;

    private static final String NEW_COMMENT = "Test comment";

    @Test
    void shouldAddNewComment() {
        Book findBook = entityManager.find(Book.class, BOOK_FIRST_ID);

        Comment addedComment = new Comment(0L, NEW_COMMENT, findBook);

        entityManager.merge(addedComment);
        commentRepositoryJpa.save(addedComment);
        entityManager.detach(addedComment);

        Comment findComment = entityManager.find(Comment.class, addedComment.getId());

        assertThat(addedComment)
                .usingRecursiveComparison()
                .isEqualTo(findComment);
    }

    @Test
    void shouldChangeComment() {
        Book secondBook = entityManager.find(Book.class, BOOK_SECOND_ID);
        Comment previousComment = entityManager.find(Comment.class, COMMENT_ID);
        entityManager.detach(previousComment);

        Comment updatedComment = commentRepositoryJpa.save(new Comment(COMMENT_ID, NEW_COMMENT, secondBook));
        Comment findComment = entityManager.find(Comment.class, COMMENT_ID);

        assertThat(updatedComment).usingRecursiveComparison()
                .isNotEqualTo(previousComment)
                .isEqualTo(findComment);
    }

    @Test
    void shouldFindExpectedCommentById() {
        Optional<Comment> optionalActualComment = commentRepositoryJpa.findById(COMMENT_ID);
        Comment expectedComment = entityManager.find(Comment.class, COMMENT_ID);

        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void shouldFindAllCommentsForFirstBook() {
        List<Comment> findComments = commentRepositoryJpa.findAllForBook(BOOK_FIRST_ID);
        List<Comment> expectedComments = getCommentsForFirstBook();

        assertThat(findComments).containsExactlyElementsOf(expectedComments);
    }

    private List<Comment> getCommentsForFirstBook() {
        return IntStream.range(1, 3).boxed()
                .map(id -> entityManager.find(Comment.class, id))
                .toList();
    }
}
