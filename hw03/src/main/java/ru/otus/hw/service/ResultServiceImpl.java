package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.QuizConfig;
import ru.otus.hw.domain.QuizResult;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final QuizConfig quizConfig;

    private final LocalizedIOService ioService;

    @Override
    public void showQuizResult(QuizResult result) {

        ioService.printLineLocalized("Result.service.result");
        ioService.prinfFormattedLineLocalized("Result.service.student",
                result.getStudent().getFullName());
        ioService.prinfFormattedLineLocalized("Result.service.answered.number",
                result.getAnsweredQuestions().size());
        ioService.prinfFormattedLineLocalized("Result.service.right.answers.number",
                result.getNumberOfCorrectAnswers());

        if (result.getNumberOfCorrectAnswers() >= quizConfig.getNumberOfCorrectAnswersToSuccessfullyPass()) {
            ioService.printLineLocalized("Result.service.success");
            return;
        }
        ioService.printLineLocalized("Result.service.fail");
    }
}
