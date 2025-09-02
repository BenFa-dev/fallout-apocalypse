package com.apocalypse.thefall.service

import com.apocalypse.thefall.entity.TerrainConfiguration
import com.apocalypse.thefall.repository.TerrainConfigurationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class TerrainConfigurationService(
    private val terrainConfigurationRepository: TerrainConfigurationRepository
) {

    @Transactional(readOnly = true)
    open fun getAllTerrainConfigurations(): List<TerrainConfiguration> = terrainConfigurationRepository.findAll()

}
