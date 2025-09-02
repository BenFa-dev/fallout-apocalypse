package com.apocalypse.thefall.dto.item

import com.apocalypse.thefall.entity.item.enums.ItemType

@JvmRecord
data class AmmoDto(
    override val id: Long?,
    override val type: ItemType?,
    override val names: Map<String, String>?,
    override val descriptions: Map<String, String>?,
    override val weight: Double,
    override val basePrice: Int,
    override val path: String?,
    val armorClassModifier: Int,
    val damageResistanceModifier: Int,
    val damageModifier: Int,
    val damageThresholdModifier: Int
) : ItemDto
