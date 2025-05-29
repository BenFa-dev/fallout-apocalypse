package com.apocalypse.thefall.dto.character.stats;

public record ConditionInstanceDto(
        Long id,
        boolean value,
        Integer conditionId
) {
}