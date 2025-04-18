package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.entity.item.enums.ItemType;

import java.util.Map;

public record AmmoDto(
        Long id,

        ItemType type,

        Map<String, String> names,

        Map<String, String> descriptions,

        double weight,

        int basePrice,

        String path,
        int armorClassModifier,
        int damageResistanceModifier,
        int damageModifier,
        int damageThresholdModifier) implements ItemDto {
}