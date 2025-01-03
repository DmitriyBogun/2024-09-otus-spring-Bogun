package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {

    private String text;

    private boolean isCorrect;
}
