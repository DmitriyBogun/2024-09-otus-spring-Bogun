package ru.otus.hw.service;

import ru.otus.hw.domain.Student;

public class StusentServiceImpl implements StudentService{
    private
    @Override
    public Student identifyStudent() {
        String firstName = ioService.readStringWithPrompt("Please input your first name");
        String lastName = ioService.readStringWithPrompt("Please input your last name");
        return new Student(firstName, lastName);
    }
}
