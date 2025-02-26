package ru.otus.hw.shell;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.ToStringConverter;
import ru.otus.hw.domain.QuizResult;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.QuizService;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final QuizService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private Student student;

    private QuizResult testResult;

    @ShellMethod(value = "Start application with shell", key = {"SA", "Start application"})
    public void startApplication() {
        login();
        test();
        results();
    }

    @ShellMethod(value = "Start identify student command", key = {"SIS", "Start identify"})
    public String login() {
        student = studentService.identifyStudent();
        testResult = null;
        return ToStringConverter.convertStudent(student);
    }

    @ShellMethod(value = "Start quiz command", key = {"SQC", "Start identify"})
    public String test() {
        testResult = testService.executeTest(student);
        return ToStringConverter.convertTestResult(testResult);
    }

    @ShellMethod(value = "Show quiz result command", key = {"SQR", "Show result"})
    public void results() {
        resultService.showQuizResult(testResult);
    }
}
