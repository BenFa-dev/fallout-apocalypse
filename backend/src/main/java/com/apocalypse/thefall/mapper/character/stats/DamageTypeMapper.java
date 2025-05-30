package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.entity.item.DamageType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DamageTypeMapper {

    DataItemDto toDto(DamageType damageType);

    List<DataItemDto> toDto(List<DamageType> damageTypes);
}