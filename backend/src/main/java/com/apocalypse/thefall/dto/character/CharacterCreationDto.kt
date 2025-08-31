package com.apocalypse.thefall.dto.character

import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto

@JvmRecord
data class CharacterCreationDto(
    val name: String?,
    val specials: MutableSet<DataIntegerItemInstanceDto?>?
) 