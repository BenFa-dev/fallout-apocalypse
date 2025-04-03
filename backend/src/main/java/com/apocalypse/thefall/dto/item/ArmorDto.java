package com.apocalypse.thefall.dto.item;

public record ArmorDto(
        Long id,
        ItemDto item,
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
        int damageResistanceElectric) {
}