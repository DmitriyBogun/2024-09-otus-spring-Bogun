package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppProperties implements QuizFileNameProvider {

    private String quizFileName;

    @Override
    public String getQuizFileName() {
        return quizFileName;
    }
}
