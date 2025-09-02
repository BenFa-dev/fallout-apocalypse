package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.PositionDto;
import com.apocalypse.thefall.dto.TileDto;
import com.apocalypse.thefall.model.TileWithState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TileMapper {

    @Mapping(target = "type", source = "tile.terrainConfiguration.name")
    @Mapping(target = "walkable", source = "tile.terrainConfiguration.walkable")
    @Mapping(target = "movementCost", source = "tile.terrainConfiguration.movementCost")
    @Mapping(target = "position", expression = "java(new PositionDto(state.tile().getX(), state.tile().getY()))")
    @Mapping(target = "descriptions", source = "tile.terrainConfiguration.descriptions")
    @Mapping(target = "id", source = "tile.id")
    TileDto toDto(TileWithState state);


    PositionDto toDto(int x, int y);

}