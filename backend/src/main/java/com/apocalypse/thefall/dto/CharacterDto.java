package com.apocalypse.thefall.dto;

import com.apocalypse.thefall.service.stats.CharacterCurrentStats;
import com.apocalypse.thefall.service.stats.CharacterStats;

public record CharacterDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        SpecialDto special,
        CharacterStats stats,
        CharacterCurrentStats currentStats) {
}