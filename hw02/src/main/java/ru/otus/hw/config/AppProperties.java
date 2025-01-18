package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties implements QuizFileNameProvider, QuizConfig {

    @Value("${test.correct.answers.number}")
    private int numberOfCorrectAnswersToSuccessfullyPass;

    @Value("${test.file.name}")
    private String testFileName;

    @Override
    public int getNumberOfCorrectAnswersToSuccessfullyPass() {
        return numberOfCorrectAnswersToSuccessfullyPass;
    }

    @Override
    public String getQuizFileName() {
        return testFileName;
    }
}
