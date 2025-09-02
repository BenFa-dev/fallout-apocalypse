package com.apocalypse.thefall.mapper.character.stats.damagetype

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.entity.item.DamageType

fun DamageType.toDataItemDto(): DataItemDto =
    DataItemDto(
        id = id,
        code = code.name,
        names = names,
        descriptions = descriptions,
        imagePath = imagePath,
        displayOrder = displayOrder,
        visible = visible,
        shortNames = null,
        camelCaseCode = code.name
    )

fun List<DamageType>.toListDataItemDto(): List<DataItemDto> =
    map { it.toDataItemDto() }
