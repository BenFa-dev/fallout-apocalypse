package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.dto.character.stats.DataTaggedItemInstanceDto;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.entity.character.stats.PerkInstance;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerkMapper {

    DataTaggedItemInstanceDto toDto(PerkInstance perkInstance);

    DataItemDto toDto(Perk perk);

    List<DataItemDto> toDto(List<Perk> perks);

}