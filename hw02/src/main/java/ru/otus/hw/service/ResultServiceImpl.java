package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.QuizConfig;
import ru.otus.hw.domain.QuizResult;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final QuizConfig quizConfig;
    private final IOService ioService;

    @Override
    public void showQuizResult(QuizResult result) {

        ioService.printLine("Quiz result: ");
        ioService.printFormattedLine("Student: %s", result.getStudent().getFullName());
        ioService.printFormattedLine("Number of answers to questions: %d", result.getAnsweredQuestions().size());
        ioService.printFormattedLine("Number of correct answers: %d", result.getNumberOfCorrectAnswers());

        if (result.getNumberOfCorrectAnswers() >= quizConfig.getNumberOfCorrectAnswersToSuccessfullyPass()) {
            ioService.printLine("В этот раз повезло!");
            return;
        }
        ioService.printLine("Критический промах!!Ошибся в выборе профессии=)");
    }
}
