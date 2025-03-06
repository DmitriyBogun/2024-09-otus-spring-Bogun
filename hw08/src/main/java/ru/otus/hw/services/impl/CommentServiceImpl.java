package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllForBook(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public Comment create(String text, String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id %d not found".formatted(bookId)));
        Comment comment = new Comment(null, text, book);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(String id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                "Не удалось найти комментарий с id = %d".formatted(id)));

        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
