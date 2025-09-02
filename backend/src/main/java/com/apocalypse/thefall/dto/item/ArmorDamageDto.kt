package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import lombok.Builder;

@Builder
public record ArmorDamageDto(
        Long id,
        DataItemDto damageType,
        Integer threshold,
        Integer resistance
) {
}