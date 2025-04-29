package com.apocalypse.thefall.dto.inventory;

import com.apocalypse.thefall.dto.item.instance.ItemInstanceDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record InventoryDto(
        Long id,
        Set<ItemInstanceDto> items,
        double currentWeight) {
}