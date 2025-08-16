package com.apocalypse.thefall.service.character.rules;

public interface CalculatedInstance<T extends Enum<T>> {
    T getCode();

    Integer getValue();

    void setCalculatedValue(Integer value);
}