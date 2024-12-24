package ru.otus.hw.exception;

public class QuestionExceptions extends RuntimeException{
    public QuestionExceptions(String message) {
        super(message);
    }

    public QuestionExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
