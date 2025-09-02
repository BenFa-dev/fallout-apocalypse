package com.apocalypse.thefall.dto

@JvmRecord
data class TerrainConfigurationDto(
    val name: String,
    val descriptions: Map<String, String>,
    val movementCost: Int,
    val walkable: Boolean,
    val path: String
) {
    fun getDescription(lang: String): String = descriptions[lang.lowercase()] ?: descriptions["en"].orEmpty()
}
