package ru.otus.hw.converter;

import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

public class ToStringConverter {

    private static final String STUDENT_AUTH = "Вы успешно зарегались на экзамен под именем: ";

    private static final String QUIZ_RESULT = "Количество успешных ответов: ";

    public static String convertStudent(Student student) {
        StringBuilder studentStringBuilder = new StringBuilder();
        studentStringBuilder.append(STUDENT_AUTH);
        studentStringBuilder.append(student.getFullName());
        return studentStringBuilder.toString();
    }

    public static String convertTestResult(QuizResult testResult) {
        StringBuilder quizResultStringBuilder = new StringBuilder();
        quizResultStringBuilder.append(QUIZ_RESULT);
        quizResultStringBuilder.append(testResult.getAnsweredQuestions().size());
        return quizResultStringBuilder.toString();
    }
}
