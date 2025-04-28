package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.SpecialDto;

public record CharacterCreationDto(
        String name,
        SpecialDto special) {
}