package com.apocalypse.thefall.service

import com.apocalypse.thefall.entity.CharacterTileDiscovery
import com.apocalypse.thefall.entity.Tile
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.repository.CharacterTileDiscoveryRepository
import org.springframework.stereotype.Service

@Service
class CharacterTileDiscoveryService(
    private val discoveryRepository: CharacterTileDiscoveryRepository
) {

    fun updateTilesDiscovery(character: Character, newlyVisibleTiles: List<Tile>) {
        val newDiscoveries = newlyVisibleTiles.map { tile ->
            CharacterTileDiscovery().apply {
                this.character = character
                this.tile = tile
            }
        }
        discoveryRepository.saveAll(newDiscoveries)
    }

    fun getDiscoveredTileIds(characterId: Long): Set<Long> =
        discoveryRepository.findByCharacterId(characterId)
            .mapNotNull { it.tile?.id }
            .toSet()
}
