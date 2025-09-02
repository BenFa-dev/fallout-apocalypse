package com.apocalypse.thefall.dto.character.stats

@JvmRecord
data class DataItemDto(
    val id: Long?,
    val code: String?,
    val names: MutableMap<String, String>?,
    val descriptions: MutableMap<String, String>?,
    val imagePath: String?,
    val displayOrder: Int?,
    val visible: Boolean,
    val shortNames: MutableMap<String, String>? = null,
    val camelCaseCode: String?
)
