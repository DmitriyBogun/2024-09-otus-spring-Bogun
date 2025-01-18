package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private QuestionDao dao;
    @Mock
    private LocalizedIOService ioService;

    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl(ioService, dao);
    }

    @Test
    void shouldAskOneQuestion() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Some answer", true));
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Test question",answers));
        when(dao.findAll()).thenReturn(questions);

        Student student = new Student("Name", "Surname");
        QuizResult quizResult = quizService.executeTest(student);

        assertThat(quizResult).isNotNull();
        assertThat(quizResult.getStudent()).isNotNull().isEqualTo(student);
        assertThatList(quizResult.getAnsweredQuestions()).isNotEmpty();
        assertThat(quizResult.getAnsweredQuestions().size()).isEqualTo(1);
        assertThat(quizResult.getNumberOfCorrectAnswers()).isEqualTo(1);
    }
}