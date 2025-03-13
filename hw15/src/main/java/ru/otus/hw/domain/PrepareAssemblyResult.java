package ru.otus.hw.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PrepareAssemblyResult {

    private CarResult carResult;

    private boolean hasPowerSteering = false;

    private boolean hasAirConditioner = false;

    public PrepareAssemblyResult(CarResult carResult) {
        this.carResult = carResult;
    }
}
