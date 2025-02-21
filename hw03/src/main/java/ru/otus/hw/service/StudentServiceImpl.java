package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService ioService;

    @Override
    public Student identifyStudent() {
        String firstName = ioService.readStringWithPromtLocalized("Student.first.name");
        String lastName = ioService.readStringWithPromtLocalized("Student.last.name");
        return new Student(firstName, lastName);
    }
}
