package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class QuizRunnerServiceImpl implements CommandLineRunner {

    private final QuizService quizService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Override
    public void run(String... args) {
        Student student = studentService.identifyStudent();
        QuizResult quizResult = quizService.executeTest(student);
        resultService.showQuizResult(quizResult);
    }
}
