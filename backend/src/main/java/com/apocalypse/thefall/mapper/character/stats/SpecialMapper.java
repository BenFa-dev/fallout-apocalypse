package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto;
import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.entity.character.stats.Special;
import com.apocalypse.thefall.entity.character.stats.SpecialInstance;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialMapper {

    DataIntegerItemInstanceDto toDto(SpecialInstance specialInstance);

    DataItemDto toDto(Special special);

    List<DataItemDto> toDto(List<Special> skills);

}