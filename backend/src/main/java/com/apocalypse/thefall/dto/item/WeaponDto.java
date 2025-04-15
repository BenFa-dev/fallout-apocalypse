package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.entity.item.enums.DamageType;
import com.apocalypse.thefall.entity.item.enums.WeaponType;

import java.util.Set;

public record WeaponDto(
        Long id,
        ItemDto item,
        WeaponType weaponType,
        int requiredStrength,
        int requiredHands,
        Integer capacity,
        DamageType damageType,
        Set<WeaponModeDto> weaponModes,
        Set<AmmoDto> compatibleAmmo
) {
}