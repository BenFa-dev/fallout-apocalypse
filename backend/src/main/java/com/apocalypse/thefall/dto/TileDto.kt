package com.apocalypse.thefall.dto

@JvmRecord
data class TileDto(
    val id: Long?,
    val position: PositionDto?,
    val type: String?,
    val walkable: Boolean,
    val movementCost: Int,
    val revealed: Boolean,
    val visible: Boolean,
    val descriptions: MutableMap<String, String>?
)
