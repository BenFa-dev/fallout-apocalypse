package com.apocalypse.thefall.dto.item;

import com.apocalypse.thefall.entity.item.enums.ItemType;

import java.util.Map;
import java.util.Set;

public record ArmorDto(
        Long id,
        ItemType type,
        Map<String, String> names,
        Map<String, String> descriptions,
        double weight,
        int basePrice,
        String path,
        int armorClass,
        Set<ArmorDamageDto> damages
) implements ItemDto {
}