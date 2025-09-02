package com.apocalypse.thefall.mapper

import com.apocalypse.thefall.dto.MapDto
import com.apocalypse.thefall.entity.GameMap

fun GameMap.toDto(): MapDto =
    MapDto(
        id = this.id,
        name = this.name,
        width = this.width,
        height = this.height,
    )