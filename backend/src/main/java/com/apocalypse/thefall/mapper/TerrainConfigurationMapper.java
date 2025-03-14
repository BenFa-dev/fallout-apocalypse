package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.TerrainConfigurationDto;
import com.apocalypse.thefall.model.TerrainConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TerrainConfigurationMapper {

    TerrainConfigurationDto toDto(TerrainConfiguration terrain);

    List<TerrainConfigurationDto> toDtoList(List<TerrainConfiguration> terrains);
}