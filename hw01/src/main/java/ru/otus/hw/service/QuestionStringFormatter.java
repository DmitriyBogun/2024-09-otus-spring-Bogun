package ru.otus.hw.service;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public final class QuestionStringFormatter {
    public static String formatQuestion(Question question) {
        if (question == null || question.getText().trim().isEmpty()) {
           return null;
        }
        String res = String.format("%s: \n", question.getText());
        if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
            return res;
        }
        int num = 1;
        for (Answer answer : question.getAnswers()) {
            if (answer.getText() == null || answer.getText().trim().isEmpty()) {
                continue;
            }
            res = res.concat(String.format("%d) %s\n", num++, answer.getText().trim()));
        }
        return res.concat("\n");
    }
}
