package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.ConditionDto;
import com.apocalypse.thefall.dto.character.stats.ConditionInstanceDto;
import com.apocalypse.thefall.entity.character.stats.Condition;
import com.apocalypse.thefall.entity.character.stats.ConditionInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    @Mapping(target = "conditionId", source = "condition.id")
    ConditionInstanceDto toDto(ConditionInstance conditionInstance);

    ConditionDto toDto(Condition condition);

    List<ConditionDto> toDto(List<Condition> conditions);
}