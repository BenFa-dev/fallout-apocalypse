package com.apocalypse.thefall.dto.item;

import java.util.Map;

public record DamageTypeDto(
        Long id,
        String code,
        Map<String, String> names,
        Map<String, String> descriptions,
        Integer displayOrder,
        Boolean visible
) {
}