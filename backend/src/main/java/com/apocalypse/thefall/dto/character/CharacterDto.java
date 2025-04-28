package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.SpecialDto;
import com.apocalypse.thefall.service.stats.CharacterStats;

public record CharacterDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        SpecialDto special,
        CharacterStats stats,
        CharacterCurrentStatsDto currentStats) {
}