package com.apocalypse.thefall.dto;

import java.util.Map;

public record TerrainConfigurationDto(
        String name,
        Map<String, String> descriptions,
        int movementCost,
        boolean walkable,
        String path) {

    public String getDescription(String lang) {
        return descriptions.getOrDefault(lang.toLowerCase(), descriptions.get("en"));
    }
}