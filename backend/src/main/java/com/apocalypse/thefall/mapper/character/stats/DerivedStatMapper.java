package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto;
import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.entity.character.stats.DerivedStat;
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DerivedStatMapper {

    @Mapping(target = "id", source = "derivedStat.id")
    @Mapping(target = "value", source = "calculatedValue")
    DataIntegerItemInstanceDto toDto(DerivedStatInstance derivedStatInstance);

    DataItemDto toDto(DerivedStat derivedStat);

    List<DataItemDto> toDto(List<DerivedStat> derivedStats);
}