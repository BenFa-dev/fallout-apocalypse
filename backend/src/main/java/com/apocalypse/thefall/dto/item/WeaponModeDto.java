package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.entity.item.enums.WeaponModeType;

public record WeaponModeDto(
        Long id,
        WeaponModeType modeType,
        Integer actionPoints,
        Integer minDamage,
        Integer maxDamage,
        Integer range,
        Integer shotsPerBurst) {
}