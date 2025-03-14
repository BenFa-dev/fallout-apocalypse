package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.PositionDto;
import com.apocalypse.thefall.dto.TileDto;
import com.apocalypse.thefall.model.Tile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TileMapper {

    @Mapping(target = "type", source = "terrainConfiguration.name")
    @Mapping(target = "walkable", source = "terrainConfiguration.walkable")
    @Mapping(target = "movementCost", source = "terrainConfiguration.movementCost")
    @Mapping(target = "position", expression = "java(toDto(tile.getX(), tile.getY()))")
    TileDto toDto(Tile tile);

    PositionDto toDto(int x, int y);

}