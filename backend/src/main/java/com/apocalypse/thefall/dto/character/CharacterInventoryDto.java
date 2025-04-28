package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.SpecialDto;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.Builder;

@Builder
public record CharacterInventoryDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        InventoryDto inventory,
        SpecialDto special,
        CharacterStats stats,
        CharacterCurrentStatsDto currentStats) {
}