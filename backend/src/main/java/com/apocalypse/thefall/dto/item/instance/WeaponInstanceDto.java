package com.apocalypse.thefall.dto.item.instance;

import com.apocalypse.thefall.dto.item.AmmoDto;
import com.apocalypse.thefall.dto.item.ItemDto;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;

public record WeaponInstanceDto(
        Long id,
        AmmoDto currentAmmoType,
        Integer currentAmmoQuantity,
        EquippedSlot equippedSlot,
        ItemDto item) {
}