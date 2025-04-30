package com.apocalypse.thefall.dto.character.stats;

public record PerkInstanceDto(
        Long id,
        Integer value,
        boolean tagged,
        Integer perkId
) {
}