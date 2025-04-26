package com.apocalypse.thefall.dto;

import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.Builder;

@Builder
public record CharacterInventoryDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        int currentActionPoints,
        int maxActionPoints,
        InventoryDto inventory,
        SpecialDto special,
        CharacterStats stats) {
}