package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class CarOrder {

    private String carBodyType;

    private String engineType;

    private String color;
}
