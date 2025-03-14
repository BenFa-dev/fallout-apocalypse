package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.MapDto;
import com.apocalypse.thefall.model.Map;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TileMapper.class)
public interface MapMapper {
    MapDto toDto(Map map);
}