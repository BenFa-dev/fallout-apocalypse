package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.SkillDto;
import com.apocalypse.thefall.dto.character.stats.SkillInstanceDto;
import com.apocalypse.thefall.entity.character.stats.Skill;
import com.apocalypse.thefall.entity.character.stats.SkillInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    @Mapping(target = "skillId", source = "skill.id")
    SkillInstanceDto toDto(SkillInstance skillInstance);

    SkillDto toDto(Skill skill);

    List<SkillDto> toDto(List<Skill> skills);

}