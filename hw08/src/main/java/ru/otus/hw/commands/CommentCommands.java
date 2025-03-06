package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    @ShellMethod(value = "Find comment by id", key = "FC")
    public String findById(String id) {
        return commentService.findById(id)
                .map(Comment::toString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all comments for book with id", key = "FCFB")
    public String findAllForBook(String id) {
        return commentService.findAllForBook(id).stream()
                .map(Comment::toString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert comment", key = "INSC")
    public String insertComment(String text, String bookId) {
        Comment savedComment = commentService.create(text, bookId);
        return savedComment.toString();
    }

    @ShellMethod(value = "Update comment", key = "UPDC")
    public String updateComment(String id, String text, String bookId) {
        Comment savedComment = commentService.update(id, text);
        return savedComment.toString();
    }

    @ShellMethod(value = "Delete comment", key = "DELC")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }
}
