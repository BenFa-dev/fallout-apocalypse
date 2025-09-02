package com.apocalypse.thefall.dto.item.instance

import com.apocalypse.thefall.dto.item.ItemDto

@JvmRecord
data class AmmoInstanceDto(
    override val id: Long?,
    val quantity: Int?,
    override val item: ItemDto?
) : ItemInstanceDto 