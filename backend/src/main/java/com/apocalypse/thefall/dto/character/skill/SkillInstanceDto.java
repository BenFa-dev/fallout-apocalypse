package com.apocalypse.thefall.dto.character.skill;

public record SkillInstanceDto(
        Long id,
        Integer value,
        boolean tagged,
        Integer skillId
) {
}