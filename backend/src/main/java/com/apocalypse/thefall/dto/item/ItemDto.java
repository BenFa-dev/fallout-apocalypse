package com.apocalypse.thefall.dto.item;


import com.apocalypse.thefall.entity.item.enums.ItemType;

import java.util.Map;

public record ItemDto(
        Long id,
        ItemType type,
        Map<String, String> names,
        Map<String, String> descriptions,
        double weight,
        int basePrice,
        String path) {

    public String getName(String lang) {
        return names.getOrDefault(lang.toLowerCase(), names.get("en"));
    }

    public String getDescription(String lang) {
        return descriptions.getOrDefault(lang.toLowerCase(), descriptions.get("en"));
    }
}