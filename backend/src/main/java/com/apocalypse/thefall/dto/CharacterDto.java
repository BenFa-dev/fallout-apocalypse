package com.apocalypse.thefall.dto;

import com.apocalypse.thefall.service.stats.CharacterCurrentStats;
import com.apocalypse.thefall.service.stats.CharacterStats;

public record CharacterDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        int currentActionPoints,
        int maxActionPoints,
        SpecialDto special,
        CharacterStats stats,
        CharacterCurrentStats currentStats) {
}