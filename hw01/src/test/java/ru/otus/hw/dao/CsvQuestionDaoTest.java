package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;

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
        CsvQuestionDao dao = new CsvQuestionDao(new AppProperties("questions.csv"));
        assertDoesNotThrow(dao::findAll);
    }
}