package com.apocalypse.thefall.dto;

import java.util.Map;

public record TileDto(
        Long id,
        PositionDto position,
        String type,
        boolean walkable,
        int movementCost,
        boolean revealed,
        boolean visible,
        Map<String, String> descriptions
) {
}
