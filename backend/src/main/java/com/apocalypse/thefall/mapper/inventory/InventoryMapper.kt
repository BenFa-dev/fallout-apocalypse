package com.apocalypse.thefall.mapper.inventory

import com.apocalypse.thefall.dto.inventory.InventoryDto
import com.apocalypse.thefall.entity.inventory.Inventory
import com.apocalypse.thefall.mapper.item.instance.toDto

fun Inventory.toDto(): InventoryDto =
    InventoryDto(
        id = id,
        items = items
            .mapNotNull { it.toDto() }
            .toMutableSet()
            .takeIf { it.isNotEmpty() },
        currentWeight = getCurrentWeight()
    )