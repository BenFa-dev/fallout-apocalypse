package com.apocalypse.thefall.dto;

public record TileDto(
        PositionDto position,
        String type,
        boolean walkable,
        int movementCost) {
}