package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exception.QuestionExceptions;

import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class CsvQuestionDaoTest {

    @MockBean
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
    void shouldThrowUnsupportedQuestionFormatException() {
        given(appProperties.getQuizFileNameByLocaleTag()).willReturn("error.test.file.csv");
        assertThrows(QuestionExceptions.class, questionDao::findAll);
    }

    @Test
    void shouldNotThrowExceptions() {
        given(appProperties.getQuizFileNameByLocaleTag()).willReturn("questions-test.csv");
        assertThatList(questionDao.findAll()).isNotNull().isNotEmpty()
                .hasSize(1);
    }
}