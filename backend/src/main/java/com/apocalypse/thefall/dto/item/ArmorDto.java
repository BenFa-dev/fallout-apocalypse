package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.entity.item.enums.ItemType;

import java.util.Map;

public record ArmorDto(
        Long id,

        ItemType type,

        Map<String, String> names,

        Map<String, String> descriptions,

        double weight,

        int basePrice,

        String path,

        int armorClass,
        int damageThresholdNormal,
        int damageThresholdLaser,
        int damageThresholdFire,
        int damageThresholdPlasma,
        int damageThresholdExplosive,
        int damageThresholdElectric,
        int damageResistanceNormal,
        int damageResistanceLaser,
        int damageResistanceFire,
        int damageResistancePlasma,
        int damageResistanceExplosive,
        int damageResistanceElectric) implements ItemDto {
}