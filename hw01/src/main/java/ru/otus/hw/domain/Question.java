package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Question {

    private String text;

    private List<Answer> answers;

}
