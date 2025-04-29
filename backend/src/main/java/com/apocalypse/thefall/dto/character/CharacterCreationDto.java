package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.character.stats.SpecialInstanceDto;

import java.util.Set;

public record CharacterCreationDto(
        String name,
        Set<SpecialInstanceDto> specials) {
}