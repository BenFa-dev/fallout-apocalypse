package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.character.stats.DataBooleanItemInstanceDto;
import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto;
import com.apocalypse.thefall.dto.character.stats.DataTaggedItemInstanceDto;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record CharacterInventoryDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        InventoryDto inventory,
        CharacterCurrentStatsDto currentStats,
        Set<DataTaggedItemInstanceDto> skills,
        Set<DataIntegerItemInstanceDto> specials,
        Set<DataTaggedItemInstanceDto> perks,
        Set<DataIntegerItemInstanceDto> derivedStats,
        Set<DataBooleanItemInstanceDto> conditions
) {
}