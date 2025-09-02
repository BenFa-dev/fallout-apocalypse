package com.apocalypse.thefall.mapper.character.stats.derived

import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto
import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.entity.character.stats.DerivedStat
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance

fun DerivedStatInstance.toDataIntegerItemInstanceDto(): DataIntegerItemInstanceDto =
    DataIntegerItemInstanceDto(
        id = this.derivedStat.id,
        value = this.calculatedValue
    )

fun DerivedStat.toDataItemDto(): DataItemDto =
    DataItemDto(
        id = this.id,
        code = this.code.name,
        names = this.names,
        descriptions = this.descriptions,
        imagePath = this.imagePath,
        displayOrder = this.displayOrder,
        visible = this.visible,
        shortNames = null,
        camelCaseCode = this.code.name
    )

fun List<DerivedStat>.toListDataItemDto(): List<DataItemDto> = this.map { it.toDataItemDto() }
