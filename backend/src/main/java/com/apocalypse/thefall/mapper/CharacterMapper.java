package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.character.*;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.CharacterCurrentStats;
import com.apocalypse.thefall.entity.character.CharacterStatusStats;
import com.apocalypse.thefall.mapper.inventory.InventoryMapper;
import com.apocalypse.thefall.service.stats.CharacterStatEngine;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
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

    public abstract CharacterCurrentStatsDto toDto(CharacterCurrentStats characterCurrentStats);

    public abstract CharacterStatusStatsDto toDto(CharacterStatusStats characterStatusStats);

    public abstract CharacterInventoryDto toCharacterInventoryDto(Character character);

    @AfterMapping
    protected void enrichStats(Character character, @MappingTarget CharacterInventoryDto.CharacterInventoryDtoBuilder builder) {
        builder.stats(characterStatEngine.compute(character));
    }

    public abstract Character fromCreationDto(CharacterCreationDto creationDto);
}
