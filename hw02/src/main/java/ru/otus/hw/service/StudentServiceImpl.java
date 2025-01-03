package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Override
    public Student identifyStudent() {
        String firstName = ioService.readStringWithPrompt("Input your first name:");
        String lastName = ioService.readStringWithPrompt("Input your last name:");
        return new Student(firstName, lastName);
    }
}
