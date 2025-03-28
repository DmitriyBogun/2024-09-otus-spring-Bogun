package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public QuizResult executeTest(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Ну что, дорогой %s ", student.getFirstName() + ", начнем опрос!");
        QuizResult quizResult = new QuizResult(student);
        for (Question question : questionDao.findAll()) {
            int numberOfAnswerVariants = question.getAnswers().size();//1
            ioService.printFormattedLine(formatQuestion(question));
            int answerVariant = ioService.readAnswerVariant(1, numberOfAnswerVariants
                    , "Открой глаза и посмотри, сколько вариантов ответов! Выбери вариант от 1 до "
                            + numberOfAnswerVariants);
            Answer answer;
            if (numberOfAnswerVariants == 1) {
                answer = question.getAnswers().get(answerVariant);
            } else {
                answer = question.getAnswers().get(answerVariant - 1);
            }
            boolean correct = answer.isCorrect();
            quizResult.applyAnswer(question, correct);
        }
        return quizResult;
    }

    private String formatQuestion(Question question) {
        if (question == null || question.getText().trim().isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.getText());
        stringBuilder.append(": \n");
        if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
            return stringBuilder.toString();
        }
        int num = 1;
        for (Answer answer : question.getAnswers()) {
            if (answer.getText() == null || answer.getText().trim().isEmpty()) {
                continue;
            }
            stringBuilder.append(num++);
            stringBuilder.append(") ");
            stringBuilder.append(answer.getText().trim());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
