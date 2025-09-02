package com.apocalypse.thefall.dto.item.instance

import com.apocalypse.thefall.dto.item.ItemDto
import com.apocalypse.thefall.entity.item.enums.EquippedSlot

@JvmRecord
data class ArmorInstanceDto(
    override val id: Long?,
    val equippedSlot: EquippedSlot?,
    override val item: ItemDto?
) : ItemInstanceDto 