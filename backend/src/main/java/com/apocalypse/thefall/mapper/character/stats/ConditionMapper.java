package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataBooleanItemInstanceDto;
import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.entity.character.stats.Condition;
import com.apocalypse.thefall.entity.character.stats.ConditionInstance;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    DataBooleanItemInstanceDto toDto(ConditionInstance conditionInstance);

    DataItemDto toDto(Condition condition);

    List<DataItemDto> toDto(List<Condition> conditions);
}