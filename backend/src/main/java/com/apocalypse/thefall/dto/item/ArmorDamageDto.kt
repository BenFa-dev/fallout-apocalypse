package com.apocalypse.thefall.dto.item

import com.apocalypse.thefall.dto.character.stats.DataItemDto

@JvmRecord
data class ArmorDamageDto(
    val id: Long?,
    val damageType: DataItemDto?,
    val threshold: Int?,
    val resistance: Int?
) 