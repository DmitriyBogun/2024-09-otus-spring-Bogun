package ru.otus.hw.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@ActiveProfiles("test")
class TestServiceImplTest {

    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private LocalizedIOService ioService;

    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl(ioService, questionDao);
    }

    @Test
    void shouldAskOneQuestion() {
        given(questionDao.findAll()).willReturn(List.of(new Question("Test question",
                List.of(new Answer("test answer", true)))));

        Student student = new Student("Name", "Surname");
        QuizResult quizResult = quizService.executeTest(student);

        assertThat(quizResult).isNotNull();
        assertThat(quizResult.getStudent()).isNotNull().isEqualTo(student);
        assertThatList(quizResult.getAnsweredQuestions()).isNotEmpty();
        assertThat(quizResult.getAnsweredQuestions().size()).isEqualTo(1);
        assertThat(quizResult.getNumberOfCorrectAnswers()).isEqualTo(1);
    }

    @Test
    void shouldNotHaveResultsForNullStudent() {
        QuizResult testResult = quizService.executeTest(null);
        Assertions.assertThat(testResult).isNotNull();
        Assertions.assertThat(testResult.getStudent()).isNull();
        assertThatList(testResult.getAnsweredQuestions()).isEmpty();
        Assertions.assertThat(testResult.getNumberOfCorrectAnswers()).isEqualTo(0);
    }
}