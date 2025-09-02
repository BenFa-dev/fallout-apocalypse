package com.apocalypse.thefall.mapper.character.stats.condition

import com.apocalypse.thefall.dto.character.stats.DataBooleanItemInstanceDto
import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.entity.character.stats.Condition
import com.apocalypse.thefall.entity.character.stats.ConditionInstance

fun ConditionInstance.toDataBooleanItemInstanceDto(): DataBooleanItemInstanceDto =
    DataBooleanItemInstanceDto(
        id = this.id,
        value = this.value
    )

fun Condition.toDataItemDto(): DataItemDto =
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

fun List<Condition>.toListDataItemDto(): List<DataItemDto> =
    this.map { it.toDataItemDto() }
