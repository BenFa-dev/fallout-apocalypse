package com.apocalypse.thefall.dto.character.skill;

import java.util.Map;

public record SkillDto(
        Long id,
        String code,
        Map<String, String> names,
        Map<String, String> descriptions,
        Integer displayOrder,
        boolean visible
) {
}
