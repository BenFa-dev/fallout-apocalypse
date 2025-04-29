package com.apocalypse.thefall.dto.character.stats;

public record SkillInstanceDto(
        Long id,
        Integer value,
        boolean tagged,
        Integer skillId
) {
}