package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.entity.item.enums.ItemType;
import com.apocalypse.thefall.entity.item.enums.WeaponType;

import java.util.Map;
import java.util.Set;

public record WeaponDto(
        Long id,
        ItemType type,
        Map<String, String> names,
        Map<String, String> descriptions,
        double weight,
        int basePrice,
        String path,
        WeaponType weaponType,
        int requiredStrength,
        int requiredHands,
        Integer capacity,
        DamageTypeDto damageType,
        Set<WeaponModeDto> weaponModes,
        Set<AmmoDto> compatibleAmmo
) implements ItemDto {
}