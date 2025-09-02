package com.apocalypse.thefall.mapper.character.stats.special

import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto
import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.entity.character.stats.Special
import com.apocalypse.thefall.entity.character.stats.SpecialInstance

fun SpecialInstance.toDataIntegerItemInstanceDto(): DataIntegerItemInstanceDto =
    DataIntegerItemInstanceDto(
        id = this.id,
        value = this.value
    )

fun Special.toDataItemDto(): DataItemDto =
    DataItemDto(
        id = this.id,
        code = this.code?.name,
        names = this.names,
        descriptions = this.descriptions,
        imagePath = this.imagePath,
        displayOrder = this.displayOrder,
        visible = this.visible,
        shortNames = this.shortNames,
        camelCaseCode = this.code?.name
    )

fun List<Special>.toListDataItemDto(): List<DataItemDto> = map { it.toDataItemDto() }
