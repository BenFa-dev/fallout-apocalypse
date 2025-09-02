package com.apocalypse.thefall.mapper

import com.apocalypse.thefall.dto.TerrainConfigurationDto
import com.apocalypse.thefall.entity.TerrainConfiguration

fun TerrainConfiguration.toDto(): TerrainConfigurationDto =
    TerrainConfigurationDto(
        name = this.name,
        descriptions = this.descriptions,
        movementCost = this.movementCost,
        walkable = this.walkable,
        path = this.path
    )

fun List<TerrainConfiguration>.toDtoList(): List<TerrainConfigurationDto> = this.map { it.toDto() }
