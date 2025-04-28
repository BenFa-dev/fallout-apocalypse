package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.SpecialDto;

public record CharacterDto(
        Long id,
        String name,
        int currentX,
        int currentY,
        SpecialDto special
) {
}