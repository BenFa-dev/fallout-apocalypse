package com.apocalypse.thefall.repository

import com.apocalypse.thefall.entity.TerrainConfiguration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TerrainConfigurationRepository : JpaRepository<TerrainConfiguration, Long>