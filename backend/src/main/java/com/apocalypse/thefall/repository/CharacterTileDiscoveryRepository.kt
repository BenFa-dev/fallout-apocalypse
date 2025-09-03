package com.apocalypse.thefall.repository

import com.apocalypse.thefall.entity.CharacterTileDiscovery
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterTileDiscoveryRepository : JpaRepository<CharacterTileDiscovery, Long> {
    fun findByCharacterId(characterId: Long): List<CharacterTileDiscovery>
}
