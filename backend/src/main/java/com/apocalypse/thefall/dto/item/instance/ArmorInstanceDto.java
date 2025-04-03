package com.apocalypse.thefall.dto.item.instance;

import com.apocalypse.thefall.dto.item.ItemDto;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;

public record ArmorInstanceDto(
        Long id,
        EquippedSlot equippedSlot,
        ItemDto item) {
}