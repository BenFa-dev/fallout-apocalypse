package com.apocalypse.thefall.mapper.character.stats.perk

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.dto.character.stats.DataTaggedItemInstanceDto
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.PerkInstance

fun PerkInstance.toDataTaggedItemInstanceDto(): DataTaggedItemInstanceDto =
    DataTaggedItemInstanceDto(
        id = this.id,
        value = this.value,
        tagged = this.active
    )

fun Perk.toDataItemDto(): DataItemDto =
    DataItemDto(
        id = this.id,
        code = this.code?.name,
        names = this.names,
        descriptions = this.descriptions,
        imagePath = this.imagePath,
        displayOrder = this.displayOrder,
        visible = this.visible,
        shortNames = null,
        camelCaseCode = this.code?.name
    )

fun List<Perk>.toListDataItemDto(): List<DataItemDto> = this.map { it.toDataItemDto() }
