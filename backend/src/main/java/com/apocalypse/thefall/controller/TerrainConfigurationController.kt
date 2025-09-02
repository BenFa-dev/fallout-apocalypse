package com.apocalypse.thefall.controller

import com.apocalypse.thefall.dto.TerrainConfigurationDto
import com.apocalypse.thefall.mapper.toDtoList
import com.apocalypse.thefall.service.TerrainConfigurationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/terrain-configurations")
class TerrainConfigurationController(
    private val terrainConfigurationService: TerrainConfigurationService
) {

    @GetMapping
    fun getAllTerrainConfigurations(): List<TerrainConfigurationDto> =
        terrainConfigurationService.getAllTerrainConfigurations().toDtoList()
}
