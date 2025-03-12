package ru.otus.hw.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CarColor {

    BLACK("black"),
    WHITE("white"),
    GREY("grey");

    private final String color;

    CarColor(String color) {
        this.color = color;
    }

    public static CarColor getFromColorName(String colorName) {
        for (CarColor carColor : CarColor.values()) {
            if (carColor.getColor().equals(colorName)) {
                return carColor;
            }
        }
        return CarColor.BLACK;
    }
}
