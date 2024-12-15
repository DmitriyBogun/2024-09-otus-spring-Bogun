package ru.otus.hw.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
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
            ioService.printLine(QuestionStringFormatter.formatQuestion(question));
        }
    }
}
