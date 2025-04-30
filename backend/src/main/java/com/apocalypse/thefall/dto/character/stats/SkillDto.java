package com.apocalypse.thefall.dto.character.stats;

import java.util.Map;

public record SkillDto(
        Long id,
        String code,
        Map<String, String> names,
        Map<String, String> descriptions,
        String imagePath,
        Integer displayOrder,
        boolean visible
) {
}
