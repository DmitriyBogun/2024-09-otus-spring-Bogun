package ru.otus.hw.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;


@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    @NonNull
    private final IOService ioService;

    @NonNull
    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        for (Question question : questionDao.findAll()) {
            ioService.printLine(formatQuestion(question));
        }
    }

    private static String formatQuestion(Question question) {
        if (question == null || question.getText().trim().isEmpty()) {
            return null;
        }
        String res = String.format("%s: \n", question.getText());
        if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
            return res;
        }
        int num = 1;
        for (Answer answer : question.getAnswers()) {
            if (answer.getText() == null || answer.getText().trim().isEmpty()) {
                continue;
            }
            res = res.concat(String.format("%d) %s\n", num++, answer.getText().trim()));
        }
        return res.concat("\n");
    }
}
