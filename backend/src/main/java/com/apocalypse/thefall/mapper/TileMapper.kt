package com.apocalypse.thefall.mapper

import com.apocalypse.thefall.dto.PositionDto
import com.apocalypse.thefall.dto.TileDto
import com.apocalypse.thefall.model.TileWithState

fun TileWithState.toDto(): TileDto {
    val t = tile
    val tc = t?.terrainConfiguration
    return TileDto(
        id = t?.id,
        position = PositionDto(t?.x ?: 0, t?.y ?: 0),
        type = tc?.name,
        walkable = tc?.walkable ?: false,
        movementCost = tc?.movementCost ?: 0,
        revealed = revealed,
        visible = visible,
        descriptions = tc?.descriptions
    )
}

fun toDto(x: Int, y: Int): PositionDto = PositionDto(x, y)
