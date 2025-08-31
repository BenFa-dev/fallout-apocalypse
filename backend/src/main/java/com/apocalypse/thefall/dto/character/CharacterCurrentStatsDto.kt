package com.apocalypse.thefall.dto.character

@JvmRecord
data class CharacterCurrentStatsDto(
    val actionPoints: Int,
    val hitPoints: Int,
    val level: Int
) 