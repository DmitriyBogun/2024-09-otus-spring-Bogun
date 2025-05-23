package ru.otus.hw.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.exceptions.EntityNotFoundException;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundEx(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleEntityValidateEx(MethodArgumentNotValidException ex) {
        final String[] errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
        return ResponseEntity.badRequest()
                .header("errorMsgs", errors)
                .build();
    }
}
