package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.CharacterCreationDto;
import com.apocalypse.thefall.dto.CharacterDto;
import com.apocalypse.thefall.dto.CharacterInventoryDto;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.mapper.inventory.InventoryMapper;
import com.apocalypse.thefall.service.stats.CharacterStatEngine;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {SpecialMapper.class, InventoryMapper.class})
public abstract class CharacterMapper {

    private CharacterStatEngine characterStatEngine;

    @Autowired
    public void setCharacterStatEngine(CharacterStatEngine characterStatEngine) {
        this.characterStatEngine = characterStatEngine;
    }

    public abstract CharacterDto toDto(Character character);

    @Mapping(target = "currentStats.actionPoints", source = "actionPoints")
    @Mapping(target = "currentStats.hitPoints", source = "hitPoints")
    public abstract CharacterInventoryDto toCharacterInventoryDto(Character character);


    @AfterMapping
    protected void enrichStats(Character character, @MappingTarget CharacterInventoryDto.CharacterInventoryDtoBuilder builder) {
        builder.stats(characterStatEngine.compute(character));
    }

    public abstract Character fromCreationDto(CharacterCreationDto creationDto);
}
