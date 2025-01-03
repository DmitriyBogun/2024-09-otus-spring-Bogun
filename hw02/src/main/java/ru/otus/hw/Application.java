package ru.otus.hw;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.QuizRunnerService;

public class Application {


    public static void main(String[] args) {

        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("spring-context.xml");
        QuizRunnerService quizRunnerService = classPathXmlApplicationContext.getBean(QuizRunnerService.class);
        quizRunnerService.run();
    }
}
