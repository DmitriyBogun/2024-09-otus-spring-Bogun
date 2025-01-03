package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class QuizRunnerServiceImpl implements QuizRunnerService {

    private final QuizService quizService;
    private final StudentService studentService;
    private final ResultService resultService;

    @Override
    public void run() {
        Student student = studentService.identifyStudent();
        QuizResult quizResult = quizService.executeTest(student);
        resultService.showQuizResult(quizResult);
    }
}
