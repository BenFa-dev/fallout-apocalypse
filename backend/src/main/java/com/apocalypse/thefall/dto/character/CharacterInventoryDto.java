package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.character.stats.*;
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
        Set<SkillInstanceDto> skills,
        Set<SpecialInstanceDto> specials,
        Set<PerkInstanceDto> perks,
        Set<DerivedStatInstanceDto> derivedStats,
        Set<ConditionInstanceDto> conditions
) {
}