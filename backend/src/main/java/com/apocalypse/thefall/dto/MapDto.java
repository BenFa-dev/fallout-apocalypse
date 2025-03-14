package com.apocalypse.thefall.dto;

import java.util.List;

public record MapDto(
        String name,
        int width,
        int height,
        List<TileDto> tiles) {
}