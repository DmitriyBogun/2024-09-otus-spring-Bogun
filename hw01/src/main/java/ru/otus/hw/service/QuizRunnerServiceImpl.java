package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizRunnerServiceImpl implements QuizRunnerService {

    private final QuizService quizService;

    @Override
    public void run() {
        quizService.executeTest();
    }
}
