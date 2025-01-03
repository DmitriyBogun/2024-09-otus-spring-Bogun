package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.QuizRunnerService;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Application {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        QuizRunnerService quizRunnerService = context.getBean(QuizRunnerService.class);
        quizRunnerService.run();
    }
}
