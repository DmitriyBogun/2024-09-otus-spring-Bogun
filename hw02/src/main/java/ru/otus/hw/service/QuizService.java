package ru.otus.hw.service;

import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

public interface QuizService {

    QuizResult executeTest(Student student);
}
