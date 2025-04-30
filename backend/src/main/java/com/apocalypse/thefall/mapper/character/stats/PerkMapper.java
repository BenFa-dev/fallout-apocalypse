package com.apocalypse.thefall.mapper.character.stats;

import com.apocalypse.thefall.dto.character.stats.PerkDto;
import com.apocalypse.thefall.dto.character.stats.PerkInstanceDto;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.entity.character.stats.PerkInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerkMapper {

    @Mapping(target = "perkId", source = "perk.id")
    PerkInstanceDto toDto(PerkInstance perkInstance);

    PerkDto toDto(Perk perk);

    List<PerkDto> toDto(List<Perk> perks);

}