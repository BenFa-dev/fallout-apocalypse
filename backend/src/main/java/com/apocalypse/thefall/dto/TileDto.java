package com.apocalypse.thefall.dto;

import java.util.Map;

public record TileDto(
        PositionDto position,
        String type,
        boolean walkable,
        int movementCost,
        boolean revealed,
        boolean visible,
        Map<String, String> descriptions
) {
}
