package com.apocalypse.thefall.dto;

import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.service.stats.CharacterCurrentStats;
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
        CharacterCurrentStats currentStats) {
}