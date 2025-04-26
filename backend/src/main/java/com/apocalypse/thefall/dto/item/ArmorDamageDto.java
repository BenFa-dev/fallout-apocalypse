package com.apocalypse.thefall.dto.item;

import lombok.Builder;

@Builder
public record ArmorDamageDto(
        Long id,
        DamageTypeDto damageType,
        Integer threshold,
        Integer resistance
) {
}