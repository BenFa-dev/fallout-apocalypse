package com.apocalypse.thefall.dto.character;

public record CharacterDto(
        Long id,
        String name,
        int currentX,
        int currentY
) {
}