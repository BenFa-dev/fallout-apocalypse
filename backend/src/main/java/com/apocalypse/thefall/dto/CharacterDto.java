package com.apocalypse.thefall.dto;

public record CharacterDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        int currentActionPoints,
        int maxActionPoints,
        SpecialDto special) {
}