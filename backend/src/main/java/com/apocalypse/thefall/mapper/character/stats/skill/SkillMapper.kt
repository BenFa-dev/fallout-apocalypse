package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.dto.character.stats.DataTaggedItemInstanceDto;
import com.apocalypse.thefall.entity.character.stats.Skill;
import com.apocalypse.thefall.entity.character.stats.SkillInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    @Mapping(target = "value", source = "calculatedValue")
    DataTaggedItemInstanceDto toDto(SkillInstance skillInstance);

    DataItemDto toDto(Skill skill);

    List<DataItemDto> toDto(List<Skill> skills);
}