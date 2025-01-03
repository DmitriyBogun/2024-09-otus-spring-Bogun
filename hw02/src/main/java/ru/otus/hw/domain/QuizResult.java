package ru.otus.hw.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuizResult {
    private Student student;
    private List<Question> answeredQuestions;
    private int numberOfCorrectAnswers;

    public QuizResult(Student student) {
        this.student = student;
        this.answeredQuestions = new ArrayList<>();
    }

    public void applyAnswer(Question question, boolean isRightAnswer) {
        answeredQuestions.add(question);
        if (isRightAnswer) {
            numberOfCorrectAnswers++;
        }
    }
}
