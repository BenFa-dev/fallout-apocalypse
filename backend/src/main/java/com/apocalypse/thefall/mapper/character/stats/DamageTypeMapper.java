package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DamageTypeDto;
import com.apocalypse.thefall.entity.item.DamageType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DamageTypeMapper {

    DamageTypeDto toDto(DamageType damageType);

    List<DamageTypeDto> toDto(List<DamageType> damageTypes);
}