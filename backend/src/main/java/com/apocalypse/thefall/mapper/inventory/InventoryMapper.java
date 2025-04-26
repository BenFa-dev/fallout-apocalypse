package com.apocalypse.thefall.mapper.inventory;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.mapper.item.instance.ItemInstanceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ItemInstanceMapper.class})
public abstract class InventoryMapper {

    private GameProperties gameProperties;

    @Autowired
    public void setGameProperties(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    @Mapping(target = "maxWeight", expression = "java(calculateMaxWeight(inventory))")
    public abstract InventoryDto toDto(Inventory inventory);

    protected double calculateMaxWeight(Inventory inventory) {
        return inventory.getMaxWeight(gameProperties);
    }
}