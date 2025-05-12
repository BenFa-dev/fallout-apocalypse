package com.apocalypse.thefall.dto.character.stats;

import java.util.Map;

public record DerivedStatDto(
        Long id,
        String code,
        String camelCaseCode,
        Map<String, String> names,
        Map<String, String> descriptions,
        String imagePath,
        Integer displayOrder,
        boolean visible
) {
}
