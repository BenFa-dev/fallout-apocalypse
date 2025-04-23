package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.CharacterCreationDto;
import com.apocalypse.thefall.dto.CharacterDto;
import com.apocalypse.thefall.entity.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDto toDto(Character character);

    @Mapping(target = "currentX", constant = "0")
    @Mapping(target = "currentY", constant = "0")
    Character fromCreationDto(CharacterCreationDto creationDto);
}