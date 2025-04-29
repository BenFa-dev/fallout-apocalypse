package com.apocalypse.thefall.mapper.inventory;

import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.mapper.item.instance.ItemInstanceMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ItemInstanceMapper.class})
public interface InventoryMapper {
    InventoryDto toDto(Inventory inventory);
}