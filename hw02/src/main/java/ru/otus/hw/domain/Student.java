package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String firstName;
    private String secondName;

    public String getFullName() {
        return String.format("%s %s", firstName, secondName);
    }
}
