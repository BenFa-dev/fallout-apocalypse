package com.apocalypse.thefall.dto.character.stats;

import java.util.Map;

public record DataItemDto(
        Long id,
        String code,
        Map<String, String> names,
        Map<String, String> descriptions,
        String imagePath,
        Integer displayOrder,
        boolean visible,
        // Optional
        Map<String, String> shortNames,
        String camelCaseCode
) {
}
