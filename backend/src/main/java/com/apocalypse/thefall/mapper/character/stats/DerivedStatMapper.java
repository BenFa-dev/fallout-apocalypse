package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DerivedStatDto;
import com.apocalypse.thefall.dto.character.stats.DerivedStatInstanceDto;
import com.apocalypse.thefall.entity.character.stats.DerivedStat;
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DerivedStatMapper {

    @Mapping(target = "derivedStatId", source = "derivedStat.id")
    @Mapping(target = "value", source = "calculatedValue")
    DerivedStatInstanceDto toDto(DerivedStatInstance derivedStatInstance);

    DerivedStatDto toDto(DerivedStat derivedStat);

    List<DerivedStatDto> toDto(List<DerivedStat> derivedStats);
}