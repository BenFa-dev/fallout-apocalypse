package com.apocalypse.thefall.mapper.character;

import com.apocalypse.thefall.dto.character.*;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.CharacterCurrentStats;
import com.apocalypse.thefall.entity.character.CharacterStatusStats;
import com.apocalypse.thefall.mapper.character.stats.DerivedStatMapper;
import com.apocalypse.thefall.mapper.character.stats.SkillMapper;
import com.apocalypse.thefall.mapper.character.stats.SpecialMapper;
import com.apocalypse.thefall.mapper.inventory.InventoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SpecialMapper.class, InventoryMapper.class, SkillMapper.class, SpecialMapper.class, DerivedStatMapper.class})
public interface CharacterMapper {

    CharacterDto toDto(Character character);

    CharacterCurrentStatsDto toDto(CharacterCurrentStats characterCurrentStats);

    CharacterStatusStatsDto toDto(CharacterStatusStats characterStatusStats);

    CharacterInventoryDto toCharacterInventoryDto(Character character);

    Character fromCreationDto(CharacterCreationDto creationDto);
}
