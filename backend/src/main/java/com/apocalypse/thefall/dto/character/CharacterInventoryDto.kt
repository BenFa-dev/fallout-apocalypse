package com.apocalypse.thefall.dto.character

import com.apocalypse.thefall.dto.character.stats.DataBooleanItemInstanceDto
import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto
import com.apocalypse.thefall.dto.character.stats.DataTaggedItemInstanceDto
import com.apocalypse.thefall.dto.inventory.InventoryDto
import lombok.Builder

@Builder
@JvmRecord
data class CharacterInventoryDto(
    val id: Long?,
    val name: String?,
    val currentX: Int,
    val currentY: Int,
    val inventory: InventoryDto?,
    val currentStats: CharacterCurrentStatsDto?,
    val skills: MutableSet<DataTaggedItemInstanceDto?>?,
    val specials: MutableSet<DataIntegerItemInstanceDto?>?,
    val perks: MutableSet<DataTaggedItemInstanceDto?>?,
    val derivedStats: MutableSet<DataIntegerItemInstanceDto?>?,
    val conditions: MutableSet<DataBooleanItemInstanceDto?>?
) 