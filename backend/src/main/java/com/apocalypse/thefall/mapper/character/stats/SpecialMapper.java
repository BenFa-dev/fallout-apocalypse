package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.SpecialDto;
import com.apocalypse.thefall.dto.character.stats.SpecialInstanceDto;
import com.apocalypse.thefall.entity.character.stats.Special;
import com.apocalypse.thefall.entity.character.stats.SpecialInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialMapper {

    @Mapping(target = "specialId", source = "special.id")
    SpecialInstanceDto toDto(SpecialInstance specialInstance);

    SpecialDto toDto(Special special);

    List<SpecialDto> toDto(List<Special> skills);

}