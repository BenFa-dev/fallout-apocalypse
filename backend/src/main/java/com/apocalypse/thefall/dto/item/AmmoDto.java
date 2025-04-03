package com.apocalypse.thefall.dto.item;

public record AmmoDto(
        Long id,
        ItemDto item,
        int armorClassModifier,
        int damageResistanceModifier,
        int damageModifier,
        int damageThresholdModifier) {
}