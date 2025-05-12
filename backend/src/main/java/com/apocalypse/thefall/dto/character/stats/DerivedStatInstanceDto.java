package com.apocalypse.thefall.dto.character.stats;

public record DerivedStatInstanceDto(
        Long id,
        Integer value,
        Integer derivedStatId
) {
}