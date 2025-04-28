package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.character.skill.SkillDto;
import com.apocalypse.thefall.dto.character.skill.SkillInstanceDto;
import com.apocalypse.thefall.entity.character.skill.Skill;
import com.apocalypse.thefall.entity.character.skill.SkillInstance;
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