package com.apocalypse.thefall.dto.item

import com.apocalypse.thefall.entity.item.enums.ItemType

@JvmRecord
data class ArmorDto(
    override val id: Long?,
    override val type: ItemType?,
    override val names: MutableMap<String, String>?,
    override val descriptions: MutableMap<String, String>?,
    override val weight: Double,
    override val basePrice: Int,
    override val path: String?,
    val armorClass: Int,
    val damages: MutableSet<ArmorDamageDto?>?
) : ItemDto 