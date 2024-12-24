package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionDaoTest {

    @Test
    void shouldThrowQuestionReadExceptionException() {
        CsvQuestionDao dao = new CsvQuestionDao(
                new AppProperties("dummyFile.csv"));
        assertThrows(Exception.class, dao::findAll);
    }

    @Test
    void shouldNotThrowExceptions() {
        CsvQuestionDao dao = new CsvQuestionDao(new AppProperties("questions-test.csv"));
        assertDoesNotThrow(dao::findAll);
    }

    @Test
    void shouldReturnValidQuestion(){
        CsvQuestionDao dao = new CsvQuestionDao(new AppProperties("questions-test.csv"));
        String testQuestion = "Why do we use Spring?";
        String testQuestionFromSource = dao.findAll().get(0).getText();
        assertEquals(testQuestion,testQuestionFromSource);
    }
}