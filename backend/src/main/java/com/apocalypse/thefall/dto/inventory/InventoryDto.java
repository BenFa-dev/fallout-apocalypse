package com.apocalypse.thefall.dto.inventory;

import com.apocalypse.thefall.dto.item.instance.ItemInstanceDto;

import java.util.Set;

public record InventoryDto(
        Long id,
        Set<ItemInstanceDto> items,
        double currentWeight,
        double maxWeight) {
}