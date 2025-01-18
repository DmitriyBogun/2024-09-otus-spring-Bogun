package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exception.QuestionExceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    private AppProperties appProperties;

    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new CsvQuestionDao(appProperties);
    }

    @Test
    void shouldThrowQuestionReadExceptionException() {
        assertThrows(QuestionExceptions.class, questionDao::findAll);
    }

    @Test
    void shouldNotThrowExceptions() {
        given(appProperties.getQuizFileNameByLocaleTagFileNameByLocaleTag()).willReturn("questions-test.csv");
        assertDoesNotThrow(questionDao::findAll);
    }

    @Test
    void shouldReturnValidQuestion(){
        given(appProperties.getQuizFileNameByLocaleTagFileNameByLocaleTag()).willReturn("questions-test.csv");
        String testQuestion = "Why do we use Spring?";
        String testQuestionFromSource = questionDao.findAll().get(0).getText();
        assertEquals(testQuestion,testQuestionFromSource);
    }

    @Test
    void shouldThrowUnsupportedQuestionFormatException() {
        given(appProperties.getQuizFileNameByLocaleTagFileNameByLocaleTag()).willReturn("error.test.questions.csv");
        assertThrows(QuestionExceptions.class, questionDao::findAll);
    }
}