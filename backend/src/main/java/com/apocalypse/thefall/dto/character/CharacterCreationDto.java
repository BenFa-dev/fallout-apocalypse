package com.apocalypse.thefall.dto.character;

import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto;

import java.util.Set;

public record CharacterCreationDto(
        String name,
        Set<DataIntegerItemInstanceDto> specials) {
}