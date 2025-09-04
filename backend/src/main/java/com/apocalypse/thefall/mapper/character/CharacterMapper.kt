package com.apocalypse.thefall.mapper.character

import com.apocalypse.thefall.dto.character.CharacterCreationDto
import com.apocalypse.thefall.dto.character.CharacterCurrentStatsDto
import com.apocalypse.thefall.dto.character.CharacterDto
import com.apocalypse.thefall.dto.character.CharacterInventoryDto
import com.apocalypse.thefall.dto.character.stats.DataIntegerItemInstanceDto
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.CharacterCurrentStats
import com.apocalypse.thefall.entity.character.stats.Special
import com.apocalypse.thefall.entity.character.stats.SpecialInstance
import com.apocalypse.thefall.mapper.character.stats.condition.toDataBooleanItemInstanceDto
import com.apocalypse.thefall.mapper.character.stats.derived.toDataIntegerItemInstanceDto
import com.apocalypse.thefall.mapper.character.stats.perk.toDataTaggedItemInstanceDto
import com.apocalypse.thefall.mapper.character.stats.skill.toDataTaggedItemInstanceDto
import com.apocalypse.thefall.mapper.character.stats.special.toDataIntegerItemInstanceDto
import com.apocalypse.thefall.mapper.inventory.toDto

/* =======================
   Entity -> DTO
   ======================= */

fun Character.toDto(): CharacterDto =
    CharacterDto(
        id = this.id,
        name = this.name,
        currentX = this.currentX,
        currentY = this.currentY
    )

fun CharacterCurrentStats.toDto(): CharacterCurrentStatsDto =
    CharacterCurrentStatsDto(
        actionPoints = this.actionPoints,
        hitPoints = this.hitPoints,
        level = this.level
    )

fun Character.toCharacterInventoryDto(): CharacterInventoryDto =
    CharacterInventoryDto(
        id = id,
        name = name,
        currentX = currentX,
        currentY = currentY,
        inventory = inventory?.toDto(),
        currentStats = currentStats.toDto(),
        skills = skills.map { it.toDataTaggedItemInstanceDto() }.toMutableSet(),
        specials = specials.map { it.toDataIntegerItemInstanceDto() }.toMutableSet(),
        perks = perks.map { it.toDataTaggedItemInstanceDto() }.toMutableSet(),
        derivedStats = derivedStats.map { it.toDataIntegerItemInstanceDto() }.toMutableSet(),
        conditions = conditions.map { it.toDataBooleanItemInstanceDto() }.toMutableSet()
    )


/* =======================
   DTO -> Entity
   ======================= */

fun CharacterCreationDto.toEntity(): Character =
    Character().apply {
        name = this@toEntity.name
        specials = this@toEntity.specials.orEmpty()
            .mapNotNull { it?.toSpecialInstanceEntity(this) }
            .toMutableSet()
    }

fun DataIntegerItemInstanceDto.toSpecialInstanceEntity(character: Character): SpecialInstance? =
    id?.let { sid ->
        SpecialInstance().apply {
            this.character = character
            this.special = Special().apply { this.id = sid }
            this.value = this@toSpecialInstanceEntity.value ?: 0
        }
    }
