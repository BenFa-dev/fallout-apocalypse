package com.apocalypse.thefall.dto.item

import com.apocalypse.thefall.entity.item.enums.ItemType


interface ItemDto {
    val id: Long?
    val type: ItemType?
    val names: Map<String, String>?
    val descriptions: Map<String, String>?
    val weight: Double?
    val basePrice: Int?
    val path: String?
}
