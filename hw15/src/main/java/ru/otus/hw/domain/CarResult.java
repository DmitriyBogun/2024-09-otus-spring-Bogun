package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class CarResult {

    private CarBodyTypes bodyTypes;

    private EngineTypes engineTypes;

    private CarColor color;
}
