package com.apocalypse.thefall.dto.item;

public record ArmorDamageDto(
        Long id,
        DamageTypeDto damageType,
        Integer threshold,
        Integer resistance
) {
}