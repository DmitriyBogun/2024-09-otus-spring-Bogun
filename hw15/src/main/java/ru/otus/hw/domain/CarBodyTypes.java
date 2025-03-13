package ru.otus.hw.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CarBodyTypes {

    SEDAN("sedan"),
    HATCHBACK("hatchback"),
    CROSSOVER("crossover");

    private final String typeName;

    CarBodyTypes(String typeName) {
        this.typeName = typeName;
    }

    public static CarBodyTypes getFromTypeName(String typeName) {
        for (CarBodyTypes type : CarBodyTypes.values()) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return CarBodyTypes.SEDAN;
    }
}
