package com.apocalypse.thefall.service.stats;

import com.apocalypse.thefall.dto.item.ArmorDamageDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record CharacterStats(
        int maxActionPoints,
        int armorClass,
        Set<ArmorDamageDto> damages
) {
}