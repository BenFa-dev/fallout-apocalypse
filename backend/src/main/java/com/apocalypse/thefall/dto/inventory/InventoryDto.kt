package com.apocalypse.thefall.dto.inventory

import com.apocalypse.thefall.dto.item.instance.ItemInstanceDto

@JvmRecord
data class InventoryDto(
    val id: Long?,
    val items: MutableSet<ItemInstanceDto>?,
    val currentWeight: Double
)