package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.character.stats.SkillInstanceDto;
import com.apocalypse.thefall.dto.character.stats.SpecialInstanceDto;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.Builder;

import java.util.Set;

@Builder
public record CharacterInventoryDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        InventoryDto inventory,
        CharacterStats stats,
        CharacterCurrentStatsDto currentStats,
        Set<SkillInstanceDto> skills,
        Set<SpecialInstanceDto> specials) {
}