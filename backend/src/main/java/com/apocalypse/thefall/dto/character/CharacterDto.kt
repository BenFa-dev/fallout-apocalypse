package com.apocalypse.thefall.dto.character

@JvmRecord
data class CharacterDto(
    val id: Long?,
    val name: String?,
    val currentX: Int,
    val currentY: Int
)
