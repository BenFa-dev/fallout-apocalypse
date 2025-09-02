package com.apocalypse.thefall.mapper.character.stats.skill

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.dto.character.stats.DataTaggedItemInstanceDto
import com.apocalypse.thefall.entity.character.stats.Skill
import com.apocalypse.thefall.entity.character.stats.SkillInstance

fun SkillInstance.toDataTaggedItemInstanceDto(): DataTaggedItemInstanceDto =
    DataTaggedItemInstanceDto(
        id = this.id,
        value = this.calculatedValue,
        tagged = this.tagged
    )

fun Skill.toDataItemDto(): DataItemDto =
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

fun List<Skill>.toListDataItemDto(): List<DataItemDto> = map { it.toDataItemDto() }