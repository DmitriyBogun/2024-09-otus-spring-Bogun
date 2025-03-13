package ru.otus.hw.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum EngineTypes {

    INLINE("in-line"),
    OPPOSITE("Opposite"),
    VTYPE("v-type");


    private final String typeName;

    EngineTypes(String typeName) {
        this.typeName = typeName;
    }

    public static EngineTypes getFromTypeName(String typeName) {
        for (EngineTypes type : EngineTypes.values()) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return EngineTypes.INLINE;
    }
}
