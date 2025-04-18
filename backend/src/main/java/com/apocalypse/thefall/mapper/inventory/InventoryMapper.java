package com.apocalypse.thefall.mapper.inventory;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.mapper.item.instance.ItemInstanceMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemInstanceMapper.class})
public interface InventoryMapper {

    @Mapping(target = "maxWeight", expression = "java(inventory.getMaxWeight(gameProperties))")
    @Mapping(target = "characterId", source = "character.id")
    InventoryDto toDto(Inventory inventory, @Context GameProperties gameProperties);
}